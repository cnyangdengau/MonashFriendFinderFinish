package com.example.yang_deng.monashfriendfindertest.Model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Yang-Deng
 */

public class StudentLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer locationId;
    private Date timeStamp;
    private String latitude;
    private String longitude;
    private String locationName;
    private StudentProfile studentNumber;

    public StudentLocation() {
    }

    public StudentLocation(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public StudentProfile getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(StudentProfile studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentLocation)) {
            return false;
        }
        StudentLocation other = (StudentLocation) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TEST.StudentLocation[ locationId=" + locationId + " ]";
    }

}

