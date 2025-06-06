package com.infy.skillbuilder.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.infy.skillbuilder.entity.Appointments;
import com.infy.skillbuilder.entity.Mentee;
import com.infy.skillbuilder.entity.Mentor;

public interface AppointmentRepository extends JpaRepository<Appointments, Integer> {
  List<Appointments> findByMentor(Mentor mentor);

  List<Appointments> findByMentee(Mentee mentee);

List<Appointments> findByMentorAndDateAndStartTime(Mentor mentor, LocalDate date, LocalTime time);

  @Query("SELECT COUNT(*) FROM Appointments WHERE mentor.mentorId=:mentorId and status=ACCEPTED")
  Integer getUpcommingSessionsByMentor(@RequestParam("mentorId") Integer mentorId);

  @Query("SELECT COUNT(*) FROM Appointments WHERE mentor.mentorId=:mentorId and status=COMPLETED")
  Integer getCompletedSessionsByMentor(@RequestParam("mentorId") Integer mentorId);

  @Query("SELECT COUNT(*) FROM Appointments WHERE mentee.menteeId=:menteeId and status=ACCEPTED")
  Integer getUpcommingSessionsByMentee(@RequestParam("menteeId") Integer menteeId);

  @Query("SELECT COUNT(*) FROM Appointments WHERE mentee.menteeId=:menteeId and status=COMPLETED")
  Integer getCompletedSessionsByMentee(@RequestParam("menteeId") Integer menteeId);

  @Query("SELECT SUM(duration) FROM Appointments WHERE mentee.menteeId=:menteeId and status=COMPLETED")
  Integer getTotalSessionDurationByMentee(@RequestParam("menteeId") Integer menteeId);

  @Query("SELECT a FROM Appointments a WHERE mentee.menteeId=:menteeId and status=ACCEPTED ORDER BY date, startTime DESC LIMIT 1")
  Appointments getNextSessionByMentee(@RequestParam("menteeId") Integer menteeId);

  @Query("SELECT COUNT(*) FROM (SELECT DISTINCT mentee as DisctinctMentees FROM Appointments WHERE mentor.mentorId=:mentorId)")
  Integer getConnectedMenteesByMentor(@RequestParam("mentorId") Integer mentorId);

  @Query("SELECT COUNT(*) FROM Appointments WHERE mentor.mentorId=:mentorId and status=COMPLETED or status=PENDING or status=ACCEPTED or status=REJECTED or status=CANCELLED")
  Integer getTotalAppointmentsByMentor(@RequestParam("mentorId") Integer mentorId);

  @Query("SELECT agenda FROM Appointments WHERE mentor.mentorId=:mentorId ORDER BY createdAt DESC")
  List<String> getLatestAgendasByMentorId(@RequestParam("mentorId") Integer mentorId);

  @Query(value = "SELECT DATE(created_at) AS day, COUNT(*) AS total_bookings FROM Appointments WHERE created_at >= CURDATE() - INTERVAL 7 DAY GROUP BY day ORDER BY day", nativeQuery = true)
  List<Object[]> findDailyBookingCountsForLast7Days();

  @Query(value = "SELECT YEAR(created_at) AS year, WEEK(created_at, 1) AS week, COUNT(*) AS total_bookings FROM Appointments WHERE created_at >= CURDATE() - INTERVAL 7 WEEK GROUP BY year, week ORDER BY year, week", nativeQuery = true)
  List<Object[]> findWeeklyBookingCountsForLast7Weeks();

  @Query(value = "SELECT YEAR(created_at) AS year, MONTH(created_at) AS month, COUNT(*) AS total_bookings FROM Appointments WHERE created_at >= CURDATE() - INTERVAL 7 MONTH GROUP BY year, month ORDER BY year, month ", nativeQuery = true)
  List<Object[]> findMonthlyBookingCountsForLast7Months();

  @Query(value = "SELECT COUNT(*) AS total_sessions, SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_sessions FROM appointments WHERE status='COMPLETED' or status='PENDING' or status='ACCEPTED' or status='REJECTED' or status='CANCELLED'", nativeQuery = true)
  List<Object[]> getSessionCompletionStats();

  @Query(value = "SELECT AVG(duration) FROM appointments", nativeQuery = true)
  Double getAverageSessionDuration();

  @Query(value = """
      WITH first_appointments AS (
       SELECT
       mentee_id,
       MIN(DATE(created_at)) AS first_date,
       DATE_FORMAT(MIN(created_at), '%Y-%m') AS cohort_month
       FROM appointments
       GROUP BY mentee_id
      ),
      cohort_sizes AS (
       SELECT
       cohort_month,
       COUNT(*) AS cohort_size
       FROM first_appointments
       GROUP BY cohort_month
      )
      SELECT
       fa.cohort_month AS cohort,
       DATE_FORMAT(a.created_at, '%Y-%m') AS activity_month,
       COUNT(DISTINCT a.mentee_id) AS active_users,
       cs.cohort_size,
       ROUND(COUNT(DISTINCT a.mentee_id) / cs.cohort_size * 100, 1) AS retention_rate
      FROM first_appointments fa
      JOIN appointments a ON fa.mentee_id = a.mentee_id
      JOIN cohort_sizes cs ON fa.cohort_month = cs.cohort_month
      WHERE a.created_at >= fa.first_date
      GROUP BY fa.cohort_month, activity_month
      ORDER BY cohort, activity_month
      """, nativeQuery = true)
  List<Object[]> getUserRetentionData();
}