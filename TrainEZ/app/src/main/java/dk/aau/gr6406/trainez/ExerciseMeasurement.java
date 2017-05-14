package dk.aau.gr6406.trainez;


/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */

import java.util.ArrayList;

/**
 * This class is a entity class for the dysphagia exercise measurements.
 * The constructor has no arguments
 */
public class ExerciseMeasurement extends Measurement {

    private int _ex1;
    private int _ex2;
    private int _ex3;
    private int _ex4;
    private int _ex5;
    private int _ex6;
    private int _ex7;
    private int _ex8;
    private int _ex9;
    private int _ex10;
    private int _ex11;
    private int _ex12;
    private int _ex13;
    private int _ex14;
    private int _ex15;
    private ArrayList<Integer> exercises;

    public ExerciseMeasurement(){

    }

    public void setExercises(ArrayList<Integer> exercises){
       this.exercises = exercises;
    }

    public ArrayList<Integer> getExercies(){
        return exercises;
    }


    public ArrayList<Integer> getAllExercises() {
        ArrayList<Integer> allExercises = new ArrayList<>();
        allExercises.add(_ex1);
        allExercises.add(_ex2);
        allExercises.add(_ex3);
        allExercises.add(_ex4);
        allExercises.add(_ex5);
        allExercises.add(_ex6);
        allExercises.add(_ex7);
        allExercises.add(_ex8);
        allExercises.add(_ex9);
        allExercises.add(_ex10);
        allExercises.add(_ex11);
        allExercises.add(_ex12);
        allExercises.add(_ex13);
        allExercises.add(_ex14);
        allExercises.add(_ex15);

        return allExercises;

    }

    // All the getters for the exercises
    public int get_ex1() {
        return _ex1;
    }

    public int get_ex2() {
        return _ex2;
    }

    public int get_ex3() {
        return _ex3;
    }

    public int get_ex4() {
        return _ex4;
    }

    public int get_ex5() {
        return _ex5;
    }

    public int get_ex6() {
        return _ex6;
    }

    public int get_ex7() {
        return _ex7;
    }

    public int get_ex8() {
        return _ex8;
    }

    public int get_ex9() {
        return _ex9;
    }

    public int get_ex10() {
        return _ex10;
    }

    public int get_ex11() {
        return _ex11;
    }

    public int get_ex12() {
        return _ex12;
    }

    public int get_ex13() {
        return _ex13;
    }

    public int get_ex14() {
        return _ex14;
    }

    public int get_ex15() {
        return _ex15;
    }
    // All the setters

    public void set_ex1(int _ex1) {
        this._ex1 = _ex1;
    }

    public void set_ex2(int _ex2) {
        this._ex2 = _ex2;
    }

    public void set_ex3(int _ex3) {
        this._ex3 = _ex3;
    }

    public void set_ex4(int _ex4) {
        this._ex4 = _ex4;
    }

    public void set_ex5(int _ex5) {
        this._ex5 = _ex5;
    }

    public void set_ex6(int _ex6) {
        this._ex6 = _ex6;
    }

    public void set_ex7(int _ex7) {
        this._ex7 = _ex7;
    }

    public void set_ex8(int _ex8) {
        this._ex8 = _ex8;
    }

    public void set_ex9(int _ex9) {
        this._ex9 = _ex9;
    }

    public void set_ex10(int _ex10) {
        this._ex10 = _ex10;
    }

    public void set_ex11(int _ex11) {
        this._ex11 = _ex11;
    }

    public void set_ex12(int _ex12) {
        this._ex12 = _ex12;
    }

    public void set_ex13(int _ex13) {
        this._ex13 = _ex13;
    }

    public void set_ex14(int _ex14) {
        this._ex14 = _ex14;
    }

    public void set_ex15(int _ex15) {
        this._ex15 = _ex15;
    }
}
