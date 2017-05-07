package com.example.yang_deng.monashfriendfindertest.Model;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Yang-Deng
 */

public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer friendshipId;

    private Date friendshipStartDate;

    private Date friendshipEndDate;

    private StudentProfile studentNumber2;

    private StudentProfile studentNumber1;

    public Friendship() {
    }

    public Friendship(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Date getFriendshipStartDate() {
        return friendshipStartDate;
    }

    public void setFriendshipStartDate(Date friendshipStartDate) {
        this.friendshipStartDate = friendshipStartDate;
    }

    public Date getFriendshipEndDate() {
        return friendshipEndDate;
    }

    public void setFriendshipEndDate(Date friendshipEndDate) {
        this.friendshipEndDate = friendshipEndDate;
    }

    public StudentProfile getStudentNumber2() {
        return studentNumber2;
    }

    public void setStudentNumber2(StudentProfile studentNumber2) {
        this.studentNumber2 = studentNumber2;
    }

    public StudentProfile getStudentNumber1() {
        return studentNumber1;
    }

    public void setStudentNumber1(StudentProfile studentNumber1) {
        this.studentNumber1 = studentNumber1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendshipId != null ? friendshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friendship)) {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.friendshipId == null && other.friendshipId != null) || (this.friendshipId != null && !this.friendshipId.equals(other.friendshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TEST.Friendship[ friendshipId=" + friendshipId + " ]";
    }

}
