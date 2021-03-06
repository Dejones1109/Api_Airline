package com.its.airport.api.communication.airport;

import com.its.airport.api.communication.AbstractCommunication;
import com.its.airport.api.communication.AirportCommunicate;
import com.its.airport.api.communication.dto.AirportRequest;
import com.its.airport.api.communication.dto.AirportResponse;
import com.its.airport.api.communication.dto.DepartureFlights;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;

@Component
@Log4j2
public class AirportCommunication extends AbstractCommunication {
    @Value("${sanve.endpoint}")
    private String baseUrl;

    private AirportCommunicate airportCommunicate;

    @PostConstruct
    public void intConnection() {
        this.airportCommunicate = buildSetting();
    }

    private AirportCommunicate buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return retrofit.create(AirportCommunicate.class);
    }


    public DepartureFlights[] searchAirport(AirportRequest airportRequest) {
        try {
            //    DepartureFlights[] flightFares=null;
            Call<AirportResponse<DepartureFlights[]>> request = airportCommunicate.searchFlight(airportRequest);
            log.info("request:{}", request);
            Response<AirportResponse<DepartureFlights[]>> response = request.execute();
            if (response.isSuccessful()) {
                log.info("response successful");
                AirportResponse<DepartureFlights[]> data = response.body();
                   DepartureFlights[] departureFlights = data.getData();

                   log.info("data:{}", data.getData());
                return departureFlights;
            } else {
                log.warn("response error!");
                return null;
            }

        } catch (Exception e) {
            log.error("error", e);
            return null;
        }
    }

}
