package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.factory.AuthorFactory;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.AuthorStatisticsMapper;
import group.iiicestseb.backend.mapper.PaperAuthorMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.utils.StringUtil;
import group.iiicestseb.backend.vo.author.*;
import group.iiicestseb.backend.vo.graph.Edge;
import group.iiicestseb.backend.vo.graph.Graph;
import group.iiicestseb.backend.vo.graph.Vertex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Author")
@Transactional(rollbackFor = Exception.class)
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private PaperAuthorMapper paperAuthorMapper;
    @Resource
    private AuthorStatisticsMapper authorStatisticsMapper;

    @Override
    public Author findAuthorByName(String name) {
        return authorMapper.selectByName(name);
    }

    @Override
    public void insertAuthorList(List<Author> authorList) {
        if (authorList.isEmpty()) {
            return;
        }
        saveBatch(authorList);
    }

    @Override
    public Collection<Author> findAuthorByIdBatch(Collection<Integer> ids) {
        return this.listByIds(ids);
    }

    @Override
    public Collection<AuthorInfoVO> findAuthorInfoByIdBatch(Collection<Integer> ids) {
        return authorMapper.selectAuthorInfoByIdBatch(ids);
    }

    @Override
    public Collection<AuthorHotInAffiliationVO> selectHotAuthorByAffiliationId(Integer id, Integer limit) {
        return authorStatisticsMapper.selectHotAuthorByAffiliationId(id, limit);
    }

    @Override
    public List<AuthorInAffiliationVO> selectAllAuthorByAffiliationId(Integer id) {
        List<Author> authorList = authorMapper.selectAllAuthorByAffiliationId(id);
        List<AuthorInAffiliationVO> authorInAffiliationVOList = new ArrayList<>();
        for (Author next : authorList) {
            authorInAffiliationVOList.add(AuthorFactory.toAuthorVO(next));
        }
        return authorInAffiliationVOList;
    }

    @Override
    public AuthorBasicInfoVO getAuthorBasicInfoByAuthorId(Integer id) {
        return authorMapper.selectAuthorBasicInfoById(id);
    }

    @Override
    public Collection<AuthorInfoVO> getAuthorPartner(Integer id, Integer limit) {
        return authorMapper.selectPartnerById(id, limit);
    }

    @Override
    public AuthorStatistics getAuthorStatisticsByAuthorId(Integer authorId) {
        return authorStatisticsMapper.selectByAuthorId(authorId);
    }

    @Override
    public Collection<AuthorStatistics> getAuthorStatisticsByAuthorIdBatch(Collection<Integer> authorIds) {
        return authorStatisticsMapper.selectByAuthorIdBatch(authorIds);
    }


    @Override
    public Graph getAuthorGraphPartner(Integer id, Integer limit) {
        Collection<AuthorVertexVO> authorVertexVOCollection = authorMapper.selectGraphPartnerById(id,limit);
        Collection<Vertex> vertexCollection = new ArrayList<>();
        Collection<Edge> edgeCollection = new ArrayList<>();
        Graph graph = new Graph();
        graph.setCenterId(id+"-author");
        graph.setName("作者合作关系图");


        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        for (var i : authorVertexVOCollection) {
            Vertex tempv = new Vertex(StringUtil.toUUID(i.getId(),Vertex.TYPE.Author.value),Vertex.TYPE.Author);
            tempv.setId(StringUtil.toUUID(i.getId(),Vertex.TYPE.Author.value));
            tempv.setName(i.getName());
            tempv.setContent(i);
            tempv.setType(Vertex.TYPE.Author.value);
            tempv.setSize(i.getPaperCount().doubleValue());
            vertexCollection.add(tempv);
            if (tempv.getId().equals(graph.getCenterId())){
                continue;
            }
            min = min >= i.getPaperCount()? i.getPaperCount():min;
            max = max <= i.getPaperCount()? i.getPaperCount():max;
            edgeCollection.add(new Edge("合作论文数："+i.getPaperCount(),i.getPaperCount().doubleValue(),graph.getCenterId(),tempv.getId(),null));
        }
        graph.getMin().put("author",min);
        graph.getMax().put("author",max);
        graph.setEdges(edgeCollection);
        graph.setVertexes(vertexCollection);
        return graph;
    }

    @Override
    public Graph getAuthorGraphAffiliation(Integer id, Integer limit) {
        Collection<AuthorVertexVO> authorVertexVOCollection = authorMapper.selectGraphAffiliationByPaperId(id,limit);
        Collection<Vertex> vertexCollection = new ArrayList<>();
        Collection<Edge> edgeCollection = new ArrayList<>();
        Graph graph = new Graph();
        graph.setCenterId(StringUtil.toUUID(id,Vertex.TYPE.Author.value));
        graph.setName("作者机构关系图");
        Collection<Integer> ids = new ArrayList<>();
        //添加机构节点
        AuthorVertexVO forAffiliationGenerator = ((ArrayList<AuthorVertexVO>)authorVertexVOCollection).get(0);
        Vertex aff_v = new Vertex(StringUtil.toUUID(forAffiliationGenerator.getAffiliationId(),Vertex.TYPE.Affiliation.value),Vertex.TYPE.Affiliation);
        aff_v.setName(forAffiliationGenerator.getName());
        aff_v.setSize(1.0);
        aff_v.setContent(null);
        vertexCollection.add(aff_v);

//        Double min = Double.MAX_VALUE;
//        Double max = Double.MIN_VALUE;
        for (var i : authorVertexVOCollection) {
            Vertex tempv = new Vertex(StringUtil.toUUID(i.getId(),Vertex.TYPE.Author.value),Vertex.TYPE.Author);
            tempv.setId(StringUtil.toUUID(i.getId(),Vertex.TYPE.Author.value));
            tempv.setName(i.getName());
            tempv.setContent(i);
            tempv.setSize(i.getScore());
            vertexCollection.add(tempv);

//            min = min >= i.getPaperCount()? i.getScore():min;
//            max = max <= i.getPaperCount()? i.getScore():max;

            edgeCollection.add(new Edge("所属",1.0,aff_v.getId(),tempv.getId(),null));
        }
//        graph.getMin().put("author",min);
//        graph.getMax().put("author",max);
        graph.setEdges(edgeCollection);
        graph.setVertexes(vertexCollection);
        return graph;
    }
}
