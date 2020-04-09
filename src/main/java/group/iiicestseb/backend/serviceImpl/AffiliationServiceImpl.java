package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.utils.StringUtil;
import group.iiicestseb.backend.vo.affiliation.AffiliationInfoVO;
import group.iiicestseb.backend.vo.graph.Edge;
import group.iiicestseb.backend.vo.graph.Graph;
import group.iiicestseb.backend.vo.graph.Vertex;
import group.iiicestseb.backend.vo.paper.SearchResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Affiliation")
@Transactional(rollbackFor = Exception.class)
public class AffiliationServiceImpl extends ServiceImpl<AffiliationMapper, Affiliation> implements AffiliationService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private AffiliationMapper affiliationMapper;

    @Override
    public Affiliation findAffiliationByName(String name) {
        return affiliationMapper.selectByName(name);
    }

    @Override
    public void saveAffiliation(Affiliation affiliation) {
        save(affiliation);
    }

    @Override
    public Collection<Affiliation> findAffiliationByIdBatch(Collection<Integer> ids) {
        return this.listByIds(ids);
    }

    @Override
    public AffiliationInfoVO selectAffiliationBasicInfoByAffiliationId(Integer id) {
        return affiliationMapper.selectBasicInfoById(id);

    }

    @Override
    public Collection<AffiliationInfoVO> findAffiliationInfoByIdBatch(Collection<Integer> ids) {
        return affiliationMapper.selectAffiliationInfoByIdBatch(ids);
    }

    @Override
    public Graph getAffiliationGraphPaperWithTerm(Integer id) {
        //创建主节点
        Collection<Vertex> vertexCollection = new ArrayList<>();
        Collection<Edge> edgeCollection = new ArrayList<>();
        Graph graph = new Graph();
        graph.setCenterId(StringUtil.toUUID(id,Vertex.TYPE.Affiliation.value));
        graph.setName("机构文献及相关领域关系图");
        //添加机构节点
        Affiliation affiliation = affiliationMapper.selectById(id);
        Vertex aff_v = new Vertex(StringUtil.toUUID(affiliation.getId(),Vertex.TYPE.Affiliation.value),Vertex.TYPE.Affiliation);
        aff_v.setName(affiliation.getName());
        aff_v.setSize(1.0);
        aff_v.setContent(null);
        vertexCollection.add(aff_v);

        //添加所有论文点
        Collection<SearchResultVO> searchResultVOCollection  = ((PaperService)regedit).getAffiliationAllPublish(id);

        HashMap<String,Integer> termCount = new HashMap<>();
        HashMap<String,String> termName = new HashMap<>();
        for (var i:searchResultVOCollection){
            Vertex paperVertex = new Vertex(StringUtil.toUUID(i.getId(),Vertex.TYPE.Paper.value),Vertex.TYPE.Paper);
            paperVertex.setId(StringUtil.toUUID(i.getId(),Vertex.TYPE.Paper.value));
            paperVertex.setName(i.getTitle());
            paperVertex.setContent(i);
            paperVertex.setSize(i.getCitationCountPaper().doubleValue());
            vertexCollection.add(paperVertex);

            edgeCollection.add(new Edge("发表",1.0,aff_v.getId(),paperVertex.getId(),null));

            //在每个论文基础上添加所有论文
            List<Term> terms = i.getTermsList();
            for (Term next : terms) {
                String uuid = StringUtil.toUUID(next.getId(), Vertex.TYPE.Term.value);
                if (!termName.containsKey(next.getName())) {
                    termName.put(uuid, next.getName());
                    termCount.put(uuid, 1);
                } else {
                    termCount.put(uuid, termCount.get(uuid) + 1);
                }
                edgeCollection.add(new Edge("相关", 1.0, paperVertex.getId(), uuid, null));
            }
        }

        for (String next : termName.keySet()) {
            Vertex tempVertex = new Vertex(next, Vertex.TYPE.Term);
            tempVertex.setName(termName.get(next));
            tempVertex.setSize(termCount.get(next).doubleValue());
            tempVertex.setContent(null);
            vertexCollection.add(tempVertex);
        }
        graph.setVertexes(vertexCollection);
        graph.setEdges(edgeCollection);
        graph.normalize();
        return graph;
    }
}
