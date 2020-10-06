package utility;

import model.Advertise;

import java.util.Objects;

public class AdvertiseData {
    private Advertise advertise;

    public AdvertiseData(Advertise advertise) {
        this.advertise = advertise;
    }

    public AdvertiseData() {
    }

    public Advertise getAdvertise() {
        return advertise;
    }

    public void setAdvertise(Advertise advertise) {
        this.advertise = advertise;
    }

    @Override
    public String toString() {
        return "AdvertiseData{" +
                "advertise=" + advertise +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertiseData that = (AdvertiseData) o;
        return Objects.equals(advertise, that.advertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertise);
    }
}
