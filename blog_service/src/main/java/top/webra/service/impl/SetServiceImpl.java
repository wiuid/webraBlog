package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.webra.mapper.SetMapper;
import top.webra.mapper.SetService;
import top.webra.pojo.Set;

import java.util.List;

@Service
public class SetServiceImpl implements SetService {

    @Autowired
    private SetMapper setMapper;
    @Override
    public Integer updSet(Set set) {
        return setMapper.updSet(set);
    }

    @Override
    public List<Set> queSet() {
        return setMapper.queSet();
    }
}
