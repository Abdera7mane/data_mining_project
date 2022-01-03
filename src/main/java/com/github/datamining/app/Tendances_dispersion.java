package app;

public class Tendances_dispersion {

        public String q1;
        public String q2;
        public String q3;
        public String min;
        public String max;
        public String ecartype;
        public String ecartQ;
        public String etendue;
        public String variance;
        public String outliers;

        public Tendances_dispersion(String q1, String q2, String q3, String ecartype, String ecartQ, String etendue, String variance, String outliers, String min, String max) {
                this.q1 = q1;
                this.q2 = q2;
                this.q3 = q3;
                this.ecartype = ecartype;
                this.ecartQ = ecartQ;
                this.etendue = etendue;
                this.variance = variance;
                this.outliers= outliers;
                this.min = min;
                this.max = max;
        }

        public String getOutliers() {
                return outliers;
        }

        public void setOutliers(String outliers) {
                this.outliers = outliers;
        }

        public String getQ1() {
                return q1;
        }

        public void setQ1(String q1) {
                this.q1 = q1;
        }

        public String getQ2() {
                return q2;
        }

        public void setQ2(String q2) {
                this.q2 = q2;
        }

        public String getQ3() {
                return q3;
        }

        public void setQ3(String q3) {
                this.q3 = q3;
        }

        public String getEcartype() {
                return ecartype;
        }

        public void setEcartype(String ecartype) {
                this.ecartype = ecartype;
        }

        public String getEcartQ() {
                return ecartQ;
        }

        public void setEcartQ(String ecartQ) {
                this.ecartQ = ecartQ;
        }

        public String getEtendue() {
                return etendue;
        }

        public void setEtendue(String etendue) {
                this.etendue = etendue;
        }

        public String getVariance() {
                return variance;
        }

        public void setVariance(String variance) {
                this.variance = variance;
        }

        public String getMin(){ return this.min; }

        public String getMax(){ return this.max; }
}
