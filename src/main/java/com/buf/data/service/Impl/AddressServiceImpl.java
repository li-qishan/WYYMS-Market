package com.buf.data.service.Impl;

import com.buf.data.dao.addressDao;
import com.buf.data.entity.AddressEntiry;
import com.buf.data.entity.CommunityAndSmallEntiry;
import com.buf.data.entity.CountryAreaEntiry;
import com.buf.data.entity.StreetAndRoadEntiry;
import com.buf.data.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private addressDao dao;

    @Override
    public List<AddressEntiry> getUserAddressDetail(String consNo, String userName) {

        return dao.getUserAddressDetail(consNo, userName);
    }

    @Override
    public List<CountryAreaEntiry> getUserCountryArea(String userName) {
        return dao.getUserCountryArea(userName);
    }

    @Override
    public List<StreetAndRoadEntiry> getUserStreet(String countyCode) {
        return dao.getUserStreet(countyCode);
    }

    @Override
    public List<StreetAndRoadEntiry> getUserRoad(String countyCode) {
        return dao.getUserRoad(countyCode);
    }

    @Override
    public List<CommunityAndSmallEntiry> getUserCommunityAndSmall(String countyCode, String type, String pCode) {

        List<CommunityAndSmallEntiry> arr = new ArrayList<CommunityAndSmallEntiry>();

        if (type.equals("Community")) {
            arr = dao.getUserCommunity(countyCode, pCode);

        }
        if (type.equals("Small")) {
            arr = dao.getUserSmall(countyCode, pCode);
        }

        return arr;
    }
}
