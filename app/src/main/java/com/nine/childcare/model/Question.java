package com.nine.childcare.model;

public class Question {
    private int id;
    private String ques, ansA, ansB, ansC, ansD, trueAns;

    public Question(int id, String ques, String ansA, String ansB, String ansC, String ansD, String trueAns) {
        this.id = id;
        this.ques = ques;
        this.ansA = ansA;
        this.ansB = ansB;
        this.ansC = ansC;
        this.ansD = ansD;
        this.trueAns = trueAns;
    }

    public int getId() {
        return id;
    }

    public String getQues() {
        return ques;
    }

    public String getAnsA() {
        return ansA;
    }

    public String getAnsB() {
        return ansB;
    }

    public String getAnsC() {
        return ansC;
    }

    public String getAnsD() {
        return ansD;
    }

    public int getTrueAns() {
        switch (trueAns.trim()) {
            case "a" :
                return 0;
            case "b" :
                return 1;
            case "c" :
                return 2;
            case "d":
                return 3;
            default:
                return -1;
        }
    }
}
