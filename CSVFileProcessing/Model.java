public class Model {
    int period;
    String birthDeath;
    String region;
    int count;

    public Model(int period, String birthDeath, String region, int count) {
        this.period = period;
        this.birthDeath = birthDeath;
        this.region = region;
        this.count = count;
    }

    public int getPeriod() {
        return period;
    }

    public String getBirthDeath() {
        return birthDeath;
    }

    public String getRegion() {
        return region;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Model{" +
                "period=" + period +
                ", birthDeath='" + birthDeath + '\'' +
                ", region='" + region + '\'' +
                ", count=" + count +
                '}';
    }
}
