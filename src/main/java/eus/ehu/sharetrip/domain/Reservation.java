package eus.ehu.sharetrip.domain;

import eus.ehu.sharetrip.configuration.UtilDate;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Integer reservationId;

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Reservation() {

    }

    private Date requestDate;
    private int numSeats;

    @ManyToOne
    private Traveler madeBy;

    @ManyToOne
    private Ride forRide;


    public Integer getReservationId() {
        return reservationId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public Traveler getMadeBy() {
        return madeBy;
    }

    public Ride getForRide() {
        return forRide;
    }

    public static class Builder {
        private Date requestDate;
        private int numSeats;
        private Traveler madeBy;
        private Ride forRide;

        public Builder() {
            Calendar today = Calendar.getInstance();
            requestDate = UtilDate.newDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH));
        }

        public Builder numSeats(int numSeats) {
            this.numSeats = numSeats;
            return this;
        }

        public Builder madeBy(Traveler madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public Builder forRide(Ride forRide) {
            this.forRide = forRide;
            return this;
        }

        public Reservation build() {
            Reservation reservation = new Reservation();
            reservation.requestDate = this.requestDate;
            reservation.numSeats = this.numSeats;
            reservation.madeBy = this.madeBy;
            reservation.forRide = this.forRide;
            return reservation;
        }
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", requestDate=" + requestDate +
                ", numSeats=" + numSeats +
                ", madeBy=" + madeBy +
                ", forRide=" + forRide +
                '}';
    }

}
