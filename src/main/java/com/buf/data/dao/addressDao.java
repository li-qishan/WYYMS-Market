package com.buf.data.dao;

import com.buf.data.dao.provider.addressProvider;
import com.buf.data.entity.AddressEntiry;
import com.buf.data.entity.CommunityAndSmallEntiry;
import com.buf.data.entity.CountryAreaEntiry;
import com.buf.data.entity.StreetAndRoadEntiry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface addressDao {


    @SelectProvider(type = addressProvider.class, method = "getUserAddressDetail")
    public List<AddressEntiry> getUserAddressDetail(
            @Param("consNo") String consNo, @Param("userName") String userName);

    @SelectProvider(type = addressProvider.class, method = "getUserCountryArea")
    public List<CountryAreaEntiry> getUserCountryArea(@Param("userName") String userName);

    @SelectProvider(type = addressProvider.class, method = "getUserStreet")
    public List<StreetAndRoadEntiry> getUserStreet(@Param("countyCode") String countyCode);

    @SelectProvider(type = addressProvider.class, method = "getUserRoad")
    public List<StreetAndRoadEntiry> getUserRoad(@Param("countyCode") String countyCode);

    @SelectProvider(type = addressProvider.class, method = "getUserCommunity")
    public List<CommunityAndSmallEntiry> getUserCommunity(@Param("countyCode") String countyCode, @Param("pCode") String pCode);

    @SelectProvider(type = addressProvider.class, method = "getUserSmall")
    public List<CommunityAndSmallEntiry> getUserSmall(@Param("countyCode") String countyCode, @Param("pCode") String pCode);
}
