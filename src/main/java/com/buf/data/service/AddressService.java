package com.buf.data.service;

import com.buf.data.entity.AddressEntiry;
import com.buf.data.entity.CommunityAndSmallEntiry;
import com.buf.data.entity.CountryAreaEntiry;
import com.buf.data.entity.StreetAndRoadEntiry;

import java.util.List;

public interface AddressService {

    List<AddressEntiry> getUserAddressDetail(String consNo, String userName);

    List<CountryAreaEntiry> getUserCountryArea(String userName);

    List<StreetAndRoadEntiry> getUserStreet(String countyCode);

    List<StreetAndRoadEntiry> getUserRoad(String countyCode);

    List<CommunityAndSmallEntiry> getUserCommunityAndSmall(String countyCode, String type, String pCode);
}
