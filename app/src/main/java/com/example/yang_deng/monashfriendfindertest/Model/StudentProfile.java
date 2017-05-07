package com.example.yang_deng.monashfriendfindertest.Model;

import android.support.annotation.Size;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yang-Deng on 22/4/17.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Yang-Deng
 */

/*public class StudentProfile implements Parcelable {

    private static final long serialVersionUID = 1L;
    private Integer studentNumber;
    private String firstName;
    private String lastName;
    private Date dob;
    private String gender;
    private String address;
    private String suburb;
    private String nationality;
    private String nativeLanguage;
    private String favouriteSport;
    private String favouriteMovie;
    private String currentJob;
    private String monashEmail;
    private String password;
    private String course;
    private String studyMode;
    private String favouriteUnitCode;
    private String subsciptionData;
    private Date subsciptionTime;

    public StudentProfile() {
    }

    public StudentProfile(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getFavouriteSport() {
        return favouriteSport;
    }

    public void setFavouriteSport(String favouriteSport) {
        this.favouriteSport = favouriteSport;
    }

    public String getFavouriteMovie() {
        return favouriteMovie;
    }

    public void setFavouriteMovie(String favouriteMovie) {
        this.favouriteMovie = favouriteMovie;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getMonashEmail() {
        return monashEmail;
    }

    public void setMonashEmail(String monashEmail) {
        this.monashEmail = monashEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public String getFavouriteUnitCode() {
        return favouriteUnitCode;
    }

    public void setFavouriteUnitCode(String favouriteUnitCode) {
        this.favouriteUnitCode = favouriteUnitCode;
    }

    public String getSubsciptionData() {
        return subsciptionData;
    }

    public void setSubsciptionData(String subsciptionData) {
        this.subsciptionData = subsciptionData;
    }

    public Date getSubsciptionTime() {
        return subsciptionTime;
    }

    public void setSubsciptionTime(Date subsciptionTime) {
        this.subsciptionTime = subsciptionTime;
    }

    protected StudentProfile(Parcel in) {
        studentNumber = in.readByte() == 0x00 ? null : in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        long tmpDob = in.readLong();
        dob = tmpDob != -1 ? new Date(tmpDob) : null;
        gender = in.readString();
        address = in.readString();
        suburb = in.readString();
        nationality = in.readString();
        nativeLanguage = in.readString();
        favouriteSport = in.readString();
        favouriteMovie = in.readString();
        currentJob = in.readString();
        monashEmail = in.readString();
        password = in.readString();
        course = in.readString();
        studyMode = in.readString();
        favouriteUnitCode = in.readString();
        subsciptionData = in.readString();
        long tmpSubsciptionTime = in.readLong();
        subsciptionTime = tmpSubsciptionTime != -1 ? new Date(tmpSubsciptionTime) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (studentNumber == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(studentNumber);
        }
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeLong(dob != null ? dob.getTime() : -1L);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(suburb);
        dest.writeString(nationality);
        dest.writeString(nativeLanguage);
        dest.writeString(favouriteSport);
        dest.writeString(favouriteMovie);
        dest.writeString(currentJob);
        dest.writeString(monashEmail);
        dest.writeString(password);
        dest.writeString(course);
        dest.writeString(studyMode);
        dest.writeString(favouriteUnitCode);
        dest.writeString(subsciptionData);
        dest.writeLong(subsciptionTime != null ? subsciptionTime.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StudentProfile> CREATOR = new Parcelable.Creator<StudentProfile>() {
        @Override
        public StudentProfile createFromParcel(Parcel in) {
            return new StudentProfile(in);
        }

        @Override
        public StudentProfile[] newArray(int size) {
            return new StudentProfile[size];
        }
    };
}*/

public class StudentProfile implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer studentNumber;
    @Size(max = 100)
    private String firstName;
    @Size(max = 100)
    private String lastName;
    private Date dob;
    @Size(max = 100)
    private String gender;
    @Size(max = 200)
    private String address;
    @Size(max = 200)
    private String suburb;
    @Size(max = 200)
    private String nationality;
    @Size(max = 200)
    private String nativeLanguage;
    @Size(max = 200)
    private String favouriteSport;
    @Size(max = 200)
    private String favouriteMovie;
    @Size(max = 200)
    private String currentJob;
    @Size(max = 400)
    private String monashEmail;
    @Size(max = 150)
    private String password;
    @Size(max = 100)
    private String course;
    @Size(max = 100)
    private String studyMode;
    @Size(max = 100)
    private String favouriteUnitCode;
    @Size(max = 500)
    private String subsciptionData;
    private Date subsciptionTime;

    public StudentProfile() {
    }

    public StudentProfile(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getFavouriteSport() {
        return favouriteSport;
    }

    public void setFavouriteSport(String favouriteSport) {
        this.favouriteSport = favouriteSport;
    }

    public String getFavouriteMovie() {
        return favouriteMovie;
    }

    public void setFavouriteMovie(String favouriteMovie) {
        this.favouriteMovie = favouriteMovie;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getMonashEmail() {
        return monashEmail;
    }

    public void setMonashEmail(String monashEmail) {
        this.monashEmail = monashEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public String getFavouriteUnitCode() {
        return favouriteUnitCode;
    }

    public void setFavouriteUnitCode(String favouriteUnitCode) {
        this.favouriteUnitCode = favouriteUnitCode;
    }

    public String getSubsciptionData() {
        return subsciptionData;
    }

    public void setSubsciptionData(String subsciptionData) {
        this.subsciptionData = subsciptionData;
    }

    public Date getSubsciptionTime() {
        return subsciptionTime;
    }

    public void setSubsciptionTime(Date subsciptionTime) {
        this.subsciptionTime = subsciptionTime;
    }
}

    /*public String getAttributesByName(String A){
        switch (A) {
            case "favouriteUnitCode":{
                return this.getFavouriteUnitCode();
            }
            case "favouriteMovie":{
                return this.getFavouriteMovie();
            }
            case "favouriteSport":{
                return this.getFavouriteSport();
            }
            case "gender":{
                return this.getGender();
            }
            case "nativeLanguage":{
                return this.getNativeLanguage();
            }
            case "course":{
                return this.getCourse();
            }
            case "currentJob":{
                return this.getCurrentJob();
            }
            case "studyMode":{
                return this.getStudyMode();
            }
            default :
                return null;
        }
    }*/



/*    public Collection<StudentLocation> getStudentLocationCollection() {
        return studentLocationCollection;
    }

    public void setStudentLocationCollection(Collection<StudentLocation> studentLocationCollection) {
        this.studentLocationCollection = studentLocationCollection;
    }

    public Collection<Friendship> getFriendshipCollection() {
        return friendshipCollection;
    }

    public void setFriendshipCollection(Collection<Friendship> friendshipCollection) {
        this.friendshipCollection = friendshipCollection;
    }

    public Collection<Friendship> getFriendshipCollection1() {
        return friendshipCollection1;
    }

    public void setFriendshipCollection1(Collection<Friendship> friendshipCollection1) {
        this.friendshipCollection1 = friendshipCollection1;
    }*/

/*    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentNumber != null ? studentNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentProfile)) {
            return false;
        }
        StudentProfile other = (StudentProfile) object;
        if ((this.studentNumber == null && other.studentNumber != null) || (this.studentNumber != null && !this.studentNumber.equals(other.studentNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TEST.StudentProfile[ studentNumber=" + studentNumber + " ]";
    }*/



/*
public class StudentProfile {
    public Integer Student_Number;
    public String First_Name;
    public String Last_Name;
    public String DOB;
    public String Gender;
    public String Address;
    public String Subsurb;
    public String Nationality;
    public String Native_Language;
    public String Favourite_Sport;
    public String Favourite_Movie;
    public String Current_Job;
    public String Monash_Email;
    public String Password;
    public String Course;
    public String Study_Mode;
    public String Favourite_Unit_Code;
    public String Subsription_Data;
    public String Subscription_Time;

    public StudentProfile(Integer Student_Number, String First_Name, String Last_Name, String DOB, String Gender, String Address
    ,String Suburb,String Nationality, String Native_Language, String Favourite_Sport, String Favourite_Movie, String Favourite_Unit_Code,
                          String Current_Job,String Monash_Email,String Password,String Course,String Study_Mode,String Subscription_Data
                          ,String Subscription_Time
    ) {
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Monash_Email = Monash_Email;
        this.Student_Number = Student_Number;
        this.Gender = Gender;
        this.Favourite_Movie = Favourite_Movie;
        this.Favourite_Sport = Favourite_Sport;
        this.Favourite_Unit_Code = Favourite_Unit_Code;
        this.Current_Job = Current_Job;
        this.Native_Language = Native_Language;
        this.Nationality = Nationality;
        this.Course = Course;
        this.DOB = DOB;
        this.Address =Address;
        this.Study_Mode = Study_Mode;
        this.Subsription_Data = Subscription_Data;
        this.Subscription_Time = Subscription_Time;
        this.Password = Password;
    }


    public Integer getStudent_Number(){return Student_Number;}
    public void setStudent_Number(Integer student_number){this.Student_Number = student_number;}
    public String getFirst_Name(){return First_Name;}
    public void setFirst_Name(String first_name){this.First_Name = first_name;}
    public String getLast_Name(){return Last_Name;}
    public void setLast_Name(String last_name){this.Last_Name = last_name;}
    public String getDOB(){return DOB;}
    public void setDOB(String dob){this.DOB = dob;}
    public String getGender(){return Gender;}
    public void setGender(String gender){this.Gender = gender;}
    public String getAddress(){return Address;}
    public void setAddress(String address){this.Address = address;}
    public String getSuburb(){return Subsurb;}
    public void setSubsurb(String suburb){this.Subsurb = suburb;}
    public String getNationality(){return Nationality;}
    public void setNationality(String nationality){this.Nationality = nationality;}
    public String getNative_Language(){return Native_Language;}
    public void setNative_Language(String native_language){this.Native_Language = native_language;}
    public String getFavourite_Sport(){return Favourite_Sport;}
    public void setFavourite_Sport(String favourite_sport){this.Favourite_Sport = favourite_sport;}
    public String getFavourite_Movie(){return Favourite_Movie;}
    public void setFavourite_Movie(String favourite_movie){this.Favourite_Movie = favourite_movie;}
    public String getFavourite_Unit_Code(){return Favourite_Unit_Code;}
    public void setFavourite_Unit_Code(String favourite_unit_code){this.Favourite_Unit_Code = favourite_unit_code;}
    public String getCurrent_Job(){return Current_Job;}
    public void setCurrent_Job(String current_job){this.Current_Job = current_job;}
    public String getMonash_Email(){return Monash_Email;}
    public void setMonash_Email(String monash_email){this.Monash_Email = monash_email;}
    public String getPassword(){return Password;}
    public void setPassword(String password){this.Password = password;}
    public String getCourse(){return Course;}
    public void setCourse(String course){this.Course = course;}
    public String getStudy_Mode(){return Study_Mode;}
    public void setStudy_Mode(String study_mode){this.Study_Mode = study_mode;}
    public String getSubsription_Data(){return Subsription_Data;}
    public void setSubsription_Data(String subsription_data){this.Subsription_Data = subsription_data;}
    public String getSubscription_Time(){return  Subscription_Time;}
    public void setSubscription_Time(String subscription_time){this.Subscription_Time = subscription_time;}
}
*/
