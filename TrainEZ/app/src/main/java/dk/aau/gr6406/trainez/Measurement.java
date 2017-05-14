package dk.aau.gr6406.trainez;


/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */

public class Measurement {


    private int _id;
    private String _personID;
    private String _date;


    public Measurement(){
    }

    public Measurement(String personID){
        this._personID=personID;
    }

    public int get_id() {
        return _id;
    }

    public String get_personID() {
        return _personID;
    }

    public String get_date() {
        return _date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_personID(String _personID) {
        this._personID = _personID;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

}

