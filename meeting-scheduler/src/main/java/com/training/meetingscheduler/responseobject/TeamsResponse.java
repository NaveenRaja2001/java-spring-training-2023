package com.training.meetingscheduler.responseobject;

public class TeamsResponse {
    private int teamId;

    private int teamCount;

    private String TeamName;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public TeamsResponse(int teamId, int teamCount, String teamName) {
        this.teamId = teamId;
        this.teamCount = teamCount;
        TeamName = teamName;
    }

    @Override
    public String toString() {
        return "TeamsResponse{" +
                "teamId=" + teamId +
                ", teamCount=" + teamCount +
                ", TeamName=" + TeamName +
                '}';
    }
}
