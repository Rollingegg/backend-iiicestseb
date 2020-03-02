package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.mapper.PaperMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
public class PaperManageController {
    @Resource
    PaperMapper paperMapper;


}
