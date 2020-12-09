package com.its.airport.api.communication;


import com.its.airport.api.communication.dto.AirportRequest;
import com.its.airport.api.communication.dto.AirportResponse;
import com.its.airport.api.communication.dto.DepartureFlights;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AirportCommunicate {
@POST("publish/lccSearchFlight")
    Call<AirportResponse<DepartureFlights[]>> searchFlight(@Body AirportRequest request);
}
