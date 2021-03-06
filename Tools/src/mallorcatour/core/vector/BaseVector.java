/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.core.vector;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Andrew
 */
public class BaseVector implements IVector {

    public static final String NUMBER_SEPARATOR = ",";
    private List<Number> values;

    public BaseVector(List<Number> values) {
        this.values = values;
    }

    public BaseVector(double... values) {
        this.values = new ArrayList<Number>();
        for (double value : values) {
            this.values.add(value);
        }
    }

	public double[] asArray() {
		double[] result = new double[values.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = values.get(i).doubleValue();
		}
		return result;
	}

    public List<Number> getValues() {
        return values;
    }

    public static BaseVector valueOf(String vector) {
        final List<Number> result = new ArrayList<Number>();
        String[] numbers = vector.split(NUMBER_SEPARATOR);
        for (String number : numbers) {
            result.add(Double.parseDouble(number));
        }
        return new BaseVector(result);
    }
}
