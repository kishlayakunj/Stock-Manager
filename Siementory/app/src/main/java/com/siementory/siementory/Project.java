package com.siementory.siementory;

/**
 * Created by Kumar Ujjwal on 16-12-2017.
 */

public class Project{
    String projectName;
    String supervisorName;

    public Project(){

    }

    public Project(String projectName, String supervisorName) {
        this.projectName=projectName;
        this.supervisorName=supervisorName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

}
