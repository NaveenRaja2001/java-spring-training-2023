package com.example.meetingscheduler.responseObject;

public class RoomsResponse {

    private int roomId;
    private String roomName;

    private int roomCount;

    public RoomsResponse(int roomId, String roomName, int roomCount) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCount = roomCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    @Override
    public String toString() {
        return "RoomsResponse{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomCount=" + roomCount +
                '}';
    }
}
