/*******************************************************************************
 * Copyright 2013 Lars Behnke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CompleteLinkageStrategy implements LinkageStrategy {

	@Override
	public Distance calculateDistance(Collection<Distance> distances) {
		double max = Double.NaN;

		for (Distance dist : distances) {
		    if (Double.isNaN(max) || dist.getDistance() > max)
		        max = dist.getDistance();
		}
		return new Distance(max);
	}
        
        @Override
	public Distance calculateDistanceK(Collection<Distance> distances,int k) {
		double max = 0.0;
                List<Double> dist_sorted = new ArrayList<>();

		for (Distance dist : distances) {
		    dist_sorted.add(dist.getDistance());
		}
                
                Collections.sort(dist_sorted);
                
                if( k > dist_sorted.size() ){
                    k = dist_sorted.size();
                }
                
                for( int i = (dist_sorted.size()-1); i > (dist_sorted.size()-1-k); i--){
                    max += dist_sorted.get(i);
                }
                max = max/k;
		return new Distance(max);
	}
}
