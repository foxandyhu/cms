package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.Company;
import com.bfly.cms.system.service.ICompanyService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:03
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CompanyServiceImpl extends BaseServiceImpl<Company, Integer> implements ICompanyService {

    @Override
    public Company getCompany() {
        List<Company> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Company company) {
        Company c = getCompany();
        if (c == null) {
            return super.save(company);
        }
        company.setId(c.getId());
        return super.edit(company);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Company company) {
        return edit(company);
    }
}
