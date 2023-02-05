package hust.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class King extends Person {
    private String dynasty;
    private String time;
    private String predecessor;
    private String successor;

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(String predecessor) {
        this.predecessor = predecessor;
    }

    public String getSuccessor() {
        return successor;
    }

    public void setSuccessor(String successor) {
        this.successor = successor;
    }

    @Override
    public void loadField(JSONObject jsonObject) {
        JSONParser parser = new JSONParser();
        jsonObject.put("tên", getName());
        jsonObject.put("sinh", getBirth());
        jsonObject.put("mất", getDeath());
        jsonObject.put("triều đại", getDynasty());
        jsonObject.put("tiền nhiệm", getPredecessor());
        jsonObject.put("kế nhiệm", getSuccessor());
        jsonObject.put("trị vì", getTime());
    }

    public King() {
        setName(null);
        setBirth(null);
        setDeath(null);
        dynasty = null;
        time = null;
        predecessor = null;
        successor = null;
    }

    public King(JSONObject jsonObject) {
        if (jsonObject.get("tên") != null)
            setName((String) jsonObject.get("tên"));
        else setName(null);
        if (jsonObject.get("sinh") != null)
            setBirth((String) jsonObject.get("sinh"));
        else setBirth(null);
        if (jsonObject.get("mất") != null)
            setDeath((String) jsonObject.get("mất"));
        else setDeath(null);
        if (jsonObject.get("triều đại") != null)
            setDeath((String) jsonObject.get("triều đại"));
        else setDeath(null);
        if (jsonObject.get("trị vì") != null)
            setDeath((String) jsonObject.get("trị vì"));
        else setDeath(null);
        if (jsonObject.get("tiền nhiệm") != null)
            setDeath((String) jsonObject.get("tiền nhiệm"));
        else setDeath(null);
        if (jsonObject.get("kế nhiệm") != null)
            setDeath((String) jsonObject.get("kế nhiệm"));
        else setDeath(null);
    }


}
