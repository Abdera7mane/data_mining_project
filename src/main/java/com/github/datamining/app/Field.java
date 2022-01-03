package app;

public class Field {
        public int id;
        public String name;
        public String description;
        public String type;
        public String range;

        public Field(int id, String name, String description, String type, String range) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.type = type;
                this.range = range;
        }

        public int getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public String getDescription() {
                return description;
        }

        public String getType() {
                return type;
        }

        public String getRange(){ return this.range; }
}
