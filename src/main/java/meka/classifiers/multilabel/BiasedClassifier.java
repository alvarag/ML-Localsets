/*
 * BiasedClassifier.java
 * Copyright (C) 2017 Burgos University, Burgos, Spain 
 * @author Álvar Arnaiz-González
 *     
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *     
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *     
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package meka.classifiers.multilabel;

import java.util.Enumeration;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.RevisionUtils;
import weka.core.Utils;

/**
 * <!-- globalinfo-start --> BiasedClassifier.<br>
 * It always returns a labelset with all zeros or ones.  
 * <p/>
 * <!-- globalinfo-end -->
 *
 * <!-- options-start --> Valid options are:
 * <p/>
 * 
 * <pre>
 * -Z &lt;zeros&gt; whether the labelset is all zeros or ones
 * </pre>
 * 
 * <!-- options-end -->
 *
 * @author Álvar Arnaiz-González (alvarag@ubu.es)
 * @version $Revision: 100 $
 */
public class BiasedClassifier extends AbstractMultiLabelClassifier implements MultiLabelClassifier {

	private static final long serialVersionUID = 8114423659191799552L;
	
	private boolean isZeros = false;
	
	private int m_NumberOfLabels;
	
	public boolean getZeros() {

		return isZeros;
	}

	public void setZeros(boolean zeros) {
		isZeros = zeros;
	}

	public String isZerosTipText() {

		return "Whether labels are all active or not.";
	}

	public String[] getOptions() {
		Vector<String> result = new Vector<String>();

		if (getZeros())
			result.add("-Z");

		return result.toArray(new String[result.size()]);
	}

	public void setOptions(String[] options) throws Exception {
		setZeros(Utils.getFlag("Z", options));
	}

	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>();

		newVector.addElement(new Option("\tSpecifies whether labels are all active or not\n" + "\t(default true)", "Z", 1, "-Z"));

		return newVector.elements();
	}

	@Override
	public void buildClassifier(Instances trainingSet) throws Exception {
		testCapabilities(trainingSet);
		
		m_NumberOfLabels = trainingSet.classIndex();
		
		if (getDebug())
			System.out.println("The training data set has " + m_NumberOfLabels + "labels.");
	}

	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double y[] = new double[m_NumberOfLabels];
		
		for (int i = 0; i < y.length; i++)
			if (getZeros())
				y[i] = 0.0;
			else
				y[i] = 1.0;

		return y;
	}

	/**
	 * Output a representation of this classifier
	 * 
	 * @return a representation of this classifier
	 */
	public String toString() {
		
		return "BiasedClassifier with " + m_NumberOfLabels + "labels.";
	}

	/**
	 * Returns the revision string.
	 * 
	 * @return the revision
	 */
	public String getRevision() {
		return RevisionUtils.extract("$Revision: 100 $");
	}

}
