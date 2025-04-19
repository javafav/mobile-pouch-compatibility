package com.mobilematching.entity;

public enum Material {
    LEATHER {
        public String toString() {
            return "Leather-Based";
        }
    },
    SILICONE {
        public String toString() {
            return "Silicone-Based";
        }
    },
    RUGGED_CASE {
        public String toString() {
            return "Rugged Protection";
        }
    },
    WOMEN_SPECIFIC {
        public String toString() {
            return "Women-Specific Design";
        }
    }
}

