package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.model.ReservationStatus;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import dk.bec.polonez.reservationsystem.repository.ReservationRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final OfferRepository offerRepository;

    private final AuthService authService;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, OfferRepository offerRepository, AuthService authService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.authService = authService;
}


    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public ArrayList<Reservation> getAllSortedByDate() {
        ArrayList<Reservation> reservations = new ArrayList<>(getAll());
        reservations.sort(Comparator.comparingLong(Reservation::getCreatedAt));

        return reservations;
    }

    public List<Reservation> getAllByUserId(Long id) {
        return reservationRepository.findByUser(userRepository.getById(id));
    }

    public Reservation getById(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        return optionalReservation
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    public ResponseReservationDto addReservation(CreateReservationDto reservationDto) {
        if(authService.isPoLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        User user = userRepository.getById(reservationDto.getUserId());
        Offer offer = offerRepository.getById(reservationDto.getOfferId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .createdAt(reservationDto.getCreatedAt())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(ReservationStatus.PREAPPROVED.name())
                .user(user)
                .offer(offer)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        ResponseReservationDto.ResponseReservationDtoBuilder response = ResponseReservationDto.builder();

        return response
                .id(savedReservation.getId())
                .createdAt(savedReservation.getCreatedAt())
                .dateFrom(savedReservation.getDateFrom())
                .dateTo(savedReservation.getDateTo())
                .status(savedReservation.getStatus())
                .userId(savedReservation.getUser().getId())
                .offerId(savedReservation.getOffer().getId())
                .build();
    }

    public ResponseReservationDto updateReservation(UpdateReservationDto reservationDto) {

        Reservation reservationExistingTest = getById(reservationDto.getId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .id(reservationDto.getId())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(reservationDto.getStatus())
                .build();

        Reservation updatedReservation = reservationRepository.save(reservation);

        ResponseReservationDto.ResponseReservationDtoBuilder response = ResponseReservationDto.builder();

        return response
                .id(updatedReservation.getId())
                .createdAt(updatedReservation.getCreatedAt())
                .dateFrom(updatedReservation.getDateFrom())
                .dateTo(updatedReservation.getDateTo())
                .status(updatedReservation.getStatus())
                .userId(updatedReservation.getUser().getId())
                .offerId(updatedReservation.getOffer().getId())
                .build();
    }

    public boolean deleteReservation(Long id) throws ResponseStatusException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        Reservation reservation;

        if(optionalReservation.isPresent())
            reservation = optionalReservation.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: This reservation not exist!");

        if(!reservation.getStatus().equals(ReservationStatus.PREAPPROVED.name()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: This reservation is already approved!");

        reservationRepository.deleteById(id);
        return true;
    }

}
