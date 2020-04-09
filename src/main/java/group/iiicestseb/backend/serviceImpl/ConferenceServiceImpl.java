package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Conference;
import group.iiicestseb.backend.mapper.ConferenceMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.ConferenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/3/25
 */
@Service("Conference")
public class ConferenceServiceImpl extends ServiceImpl<BaseMapper<Conference>,Conference> implements ConferenceService {
    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private ConferenceMapper conferenceMapper;

    @Override
    public Conference findConferenceByName(String name) {
        return conferenceMapper.findByName(name);
    }

    @Override
    public void insertConference(Conference c) {
        conferenceMapper.insert(c);
    }

    @Override
    public Conference findConferenceById(Integer conferenceId) {
        return conferenceMapper.selectById(conferenceId);
    }
}
