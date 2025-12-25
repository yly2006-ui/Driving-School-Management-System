package com.sample;


public class DrivingLocation {

  private long locationId;
  private String locationName;
  private long provinceId;
  private String locationTypeId;
  private long locationNo;
  private String capacity;
  private String status;
  private String delFlag;
  private long cityId;
  private String master;
  private long districtId;


  public long getLocationId() {
    return locationId;
  }

  public void setLocationId(long locationId) {
    this.locationId = locationId;
  }


  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }


  public long getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(long provinceId) {
    this.provinceId = provinceId;
  }


  public String getLocationTypeId() {
    return locationTypeId;
  }

  public void setLocationTypeId(String locationTypeId) {
    this.locationTypeId = locationTypeId;
  }


  public long getLocationNo() {
    return locationNo;
  }

  public void setLocationNo(long locationNo) {
    this.locationNo = locationNo;
  }


  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(String delFlag) {
    this.delFlag = delFlag;
  }


  public long getCityId() {
    return cityId;
  }

  public void setCityId(long cityId) {
    this.cityId = cityId;
  }


  public String getMaster() {
    return master;
  }

  public void setMaster(String master) {
    this.master = master;
  }


  public long getDistrictId() {
    return districtId;
  }

  public void setDistrictId(long districtId) {
    this.districtId = districtId;
  }

}
