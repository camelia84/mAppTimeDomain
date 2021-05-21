package com.mAppTime.mAppTimeDomain.dao;

import com.mAppTime.mAppTimeDomain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {

    @Query(value= "SELECT * FROM Event e ORDER BY e.dtEnd ASC")
    public List<Event> findEventsByOrder();

    public List<Event> findByOrderBydtEndAsc();

    @Query(value = "SELECT * FROM events WHERE  dt_start = :dtStart AND dt_end = :dtEnd ",  nativeQuery = true)
    public List<Event> findEventByDate(Long dtStart, Long dtEnd);
}
