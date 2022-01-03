package app;

public class Instance {
        public int id;
        public double field1;
        public double field2;
        public double field3;
        public double field4;
        public double field5;
        public double field6;
        public double field7;
        public String classe;

        public Instance(int id, double field1, double field2, double field3, double field4, double field5, double field6, double field7, String classe) {
                this.id = id;
                this.field1 = field1;
                this.field2 = field2;
                this.field3 = field3;
                this.field4 = field4;
                this.field5 = field5;
                this.field6 = field6;
                this.field7 = field7;
                this.classe = classe;
        }

        public int getId() {
                return id;
        }

        public double getField1() {
                return field1;
        }

        public double getField2() {
                return field2;
        }

        public double getField3() {
                return field3;
        }

        public double getField4() {
                return field4;
        }

        public double getField5() {
                return field5;
        }

        public double getField6() {
                return field6;
        }

        public double getField7() {
                return field7;
        }

        public String getClasse() {
                return classe;
        }
}
