package dk.aau.gr6406.trainez;

/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */

/**
 * This class is a entity class for the weight measurements.
 * You can set the values by using the constructor or setters
 */
public class WeightMeasurement extends Measurement {

    private float _kilo;

    public WeightMeasurement() {
    }

    public float get_kilo() {
        return _kilo;
    }

    public void set_kilo(float _kilo) {
        this._kilo = _kilo;
    }

    public WeightMeasurement(float kilo) {
        this._kilo = kilo;
    }

}
