package restaurantmgr.service;

import java.util.List;
import java.util.Optional;

import restaurantmgr.model.Reservation;

public interface ReservationInterface {
	public int addNewReservation(Reservation reservation);
	public boolean cancelReservation(int reservationId);
	public Optional<Reservation> lookupReservation(int reservationId);
	public Optional<List<Reservation>> findReservations();
}
