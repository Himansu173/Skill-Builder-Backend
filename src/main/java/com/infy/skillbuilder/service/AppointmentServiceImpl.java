package com.infy.skillbuilder.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.AppointmentsDTO;
import com.infy.skillbuilder.dto.BookingStatus;
import com.infy.skillbuilder.dto.DailyBookingCounts;
import com.infy.skillbuilder.dto.MenteeDTO;
import com.infy.skillbuilder.dto.MentorDTO;
import com.infy.skillbuilder.dto.MonthlyBookingCounts;
import com.infy.skillbuilder.dto.RejectionReasons;
import com.infy.skillbuilder.dto.ReviewsDTO;
import com.infy.skillbuilder.dto.Status;
import com.infy.skillbuilder.dto.UserRetentionDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.dto.WeeklyBookingCounts;
import com.infy.skillbuilder.entity.Appointments;
import com.infy.skillbuilder.entity.Mentee;
import com.infy.skillbuilder.entity.Mentor;
import com.infy.skillbuilder.entity.Notifications;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.AppointmentRepository;
import com.infy.skillbuilder.repository.MenteeRepository;
import com.infy.skillbuilder.repository.MentorRepository;
import com.infy.skillbuilder.repository.NotificationsRepository;

import jakarta.transaction.Transactional;

@Service("appointmentService")
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
      @Autowired
      private AppointmentRepository appointmentRepository;
      @Autowired
      private MenteeRepository menteeRepository;
      @Autowired
      private MentorRepository mentorRepository;
      @Autowired
      private NotificationsRepository notificationsRepository;

      private ModelMapper mapper = new ModelMapper();

      private String userNotFound = "Service.USER_NOT_FOUND";
      private String appointmentNotFound = "Service.APPOINTMENT_NOT_FOUND";

      @Override
      public Integer bookAppointment(AppointmentsDTO appointmentsDTO) throws SkillBuilderException {
            Appointments appointment = mapper.map(appointmentsDTO, Appointments.class);
            Mentor mentor = mentorRepository.findById(appointmentsDTO.getMentor().getMentorId())
                        .orElseThrow(() -> new SkillBuilderException(userNotFound));
            Mentee mentee = menteeRepository.findById(appointmentsDTO.getMentee().getMenteeId())
                        .orElseThrow(() -> new SkillBuilderException(userNotFound));

            appointment.setMentor(mentor);
            appointment.setMentee(mentee);
            appointment.setStatus(Status.PENDING);
            appointment.setCreatedAt(LocalDateTime.now());
            for (int i = 0; i < mentor.getMentorAvailability().size(); i++) {
                  if (mentor.getMentorAvailability().get(i).getDate().equals(appointment.getDate()) &&
                              mentor.getMentorAvailability().get(i).getStartTime().equals(appointment.getStartTime()) &&
                              mentor.getMentorAvailability().get(i).getEndTime().equals(appointment.getEndTime()) &&
                              mentor.getMentorAvailability().get(i).getStatus().equals(BookingStatus.AVAILABLE)) {
                        Integer id = appointmentRepository.save(appointment).getAppointmentId();
                        if (id != null) {
                              Notifications notification = new Notifications();
                              notification.setSenderId(mentee.getMenteeId());
                              notification.setSenderType(UserType.MENTEE);
                              notification.setReceiverId(mentor.getMentorId());
                              notification.setReceiverType(UserType.MENTOR);
                              notification.setMessage("New session request from " + mentee.getName() + " on date "
                                          + appointment.getDate() + " time " + appointment.getStartTime() + " to "
                                          + appointment.getEndTime());
                              notification.setIsRead(false);
                              notification.setCreatedAt(LocalDateTime.now());
                              notificationsRepository.save(notification);
                        }
                        return id;
                  }
            }
            throw new SkillBuilderException("Service.MENTOR_UNAVAILABLE_FOR_BOOKING");
      }

      @Override
      public List<AppointmentsDTO> getAppointmentsByMentor(Integer id) throws SkillBuilderException {
            Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
            return appointmentRepository.findByMentor(mentor).stream().map(a -> {
                  AppointmentsDTO app = new AppointmentsDTO();
                  app.setAppointmentId(a.getAppointmentId());
                  app.setAgenda(a.getAgenda());
                  app.setDuration(a.getDuration());
                  app.setStartTime(a.getStartTime());
                  app.setEndTime(a.getEndTime());
                  app.setStatus(a.getStatus());
                  app.setDate(a.getDate());
                  app.setCreatedAt(a.getCreatedAt());
                  app.setNoteByMentee(a.getNoteByMentee());
                  app.setReasonByMentorIfRejecting(a.getReasonByMentorIfRejecting());
                  app.setMentee(new MenteeDTO());
                  app.getMentee().setMenteeId(a.getMentee().getMenteeId());
                  app.setMentor(new MentorDTO());
                  app.getMentor().setMentorId(a.getMentor().getMentorId());
                  if (a.getReview() != null) {
                        app.setReview(mapper.map(a.getReview(), ReviewsDTO.class));
                  }
                  return app;
            }).toList();
      }

      @Override
      public List<AppointmentsDTO> getAppointmentsByMentee(Integer id) throws SkillBuilderException {
            Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
            return appointmentRepository.findByMentee(mentee).stream().map(a -> {
                  AppointmentsDTO app = new AppointmentsDTO();
                  app.setAppointmentId(a.getAppointmentId());
                  app.setAgenda(a.getAgenda());
                  app.setDuration(a.getDuration());
                  app.setStartTime(a.getStartTime());
                  app.setEndTime(a.getEndTime());
                  app.setStatus(a.getStatus());
                  app.setDate(a.getDate());
                  app.setCreatedAt(a.getCreatedAt());
                  app.setNoteByMentee(a.getNoteByMentee());
                  app.setReasonByMentorIfRejecting(a.getReasonByMentorIfRejecting());
                  app.setMentee(new MenteeDTO());
                  app.getMentee().setMenteeId(a.getMentee().getMenteeId());
                  app.setMentor(new MentorDTO());
                  app.getMentor().setMentorId(a.getMentor().getMentorId());
                  if (a.getReview() != null) {
                        app.setReview(mapper.map(a.getReview(), ReviewsDTO.class));
                  }
                  return app;
            }).toList();
      }

      public void cancelPreviousAppointmentById(Integer id) throws SkillBuilderException {
            Appointments prevAppointment = appointmentRepository.findById(id)
                        .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
            Mentor mentor = mentorRepository.findById(prevAppointment.getMentor().getMentorId())
                        .orElseThrow(() -> new SkillBuilderException(userNotFound));
            if (!prevAppointment.getStatus().equals(Status.ACCEPTED)) {
                  throw new SkillBuilderException("Service.PREVIOUS_APPOINTMENT_NOT_ACCEPTED");
            }
            for (int j = 0; j < mentor.getMentorAvailability().size(); j++) {
                  if (mentor.getMentorAvailability().get(j).getDate().equals(prevAppointment.getDate()) &&
                              mentor.getMentorAvailability().get(j).getStartTime()
                                          .equals(prevAppointment.getStartTime())
                              &&
                              mentor.getMentorAvailability().get(j).getEndTime().equals(prevAppointment.getEndTime())) {
                        mentor.getMentorAvailability().get(j).setStatus(BookingStatus.AVAILABLE);
                        prevAppointment.setStatus(Status.CANCELLED);
                        return;
                  }
            }
            throw new SkillBuilderException("Service.UNABLE_TO_CANCLE_PREVIOUS_APPOINTMENT");
      }

      public void cancelAppointmentsByDate(Integer id, Mentor mentor, LocalDate date, LocalTime time)
                  throws SkillBuilderException {
            List<Appointments> appointments = appointmentRepository.findByMentorAndDateAndStartTime(mentor, date, time);
            if (appointments != null)
                  appointments.stream()
                              .filter(a -> !a.getAppointmentId().equals(id))
                              .forEach(a -> a.setStatus(Status.REJECTED));
      }

      @Override
      public void acceptBooking(Integer appointmentId) throws SkillBuilderException {
            Appointments appointment = appointmentRepository.findById(appointmentId)
                        .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
            Mentor mentor = mentorRepository.findById(appointment.getMentor().getMentorId())
                        .orElseThrow(() -> new SkillBuilderException(userNotFound));
            for (int i = 0; i < mentor.getMentorAvailability().size(); i++) {
                  if (mentor.getMentorAvailability().get(i).getDate().equals(appointment.getDate()) &&
                              mentor.getMentorAvailability().get(i).getStartTime().equals(appointment.getStartTime()) &&
                              mentor.getMentorAvailability().get(i).getEndTime().equals(appointment.getEndTime())) {

                        if (appointment.getStatus().equals(Status.RESCHEDULE_REQUESTED)) {
                              cancelPreviousAppointmentById(appointment.getRescheduledId());
                        }

                        cancelAppointmentsByDate(appointment.getAppointmentId(), appointment.getMentor(),
                                    appointment.getDate(), appointment.getStartTime());

                        Notifications notification = new Notifications();
                        notification.setSenderId(mentor.getMentorId());
                        notification.setSenderType(UserType.MENTOR);
                        notification.setReceiverId(appointment.getMentee().getMenteeId());
                        notification.setReceiverType(UserType.MENTEE);
                        if (appointment.getStatus().equals(Status.RESCHEDULE_REQUESTED)) {
                              Appointments prevAppointment = appointmentRepository
                                          .findById(appointment.getRescheduledId())
                                          .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
                              notification.setMessage("Session reschedule request on date " + appointment.getDate()
                                          + " time " + appointment.getStartTime() + " to " + appointment.getEndTime()
                                          + " is accepted by the mentor " + mentor.getName()
                                          + ", and the previous appointment on date " + prevAppointment.getDate()
                                          + " is cancled.");
                        } else {
                              notification.setMessage("Session request on date " + appointment.getDate() + " time "
                                          + appointment.getStartTime() + " to " + appointment.getEndTime()
                                          + " is accepted by the mentor " + mentor.getName());
                        }
                        notification.setIsRead(false);
                        notification.setCreatedAt(LocalDateTime.now());
                        notificationsRepository.save(notification);

                        mentor.getMentorAvailability().get(i).setStatus(BookingStatus.UNAVAILABLE);
                        appointment.setStatus(Status.ACCEPTED);
                        break;
                  }
            }
      }

      @Override
      public void rejectBooking(Integer appointmentId, RejectionReasons reason) throws SkillBuilderException {
            Appointments appointment = appointmentRepository.findById(appointmentId)
                        .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
            if (appointment.getStatus().equals(Status.ACCEPTED)) {
                  throw new SkillBuilderException("Service.APPOINTMENT_ACCEPTED_ALREADY");
            }
            Notifications notification = new Notifications();
            notification.setSenderId(appointment.getMentor().getMentorId());
            notification.setSenderType(UserType.MENTOR);
            notification.setReceiverId(appointment.getMentee().getMenteeId());
            notification.setReceiverType(UserType.MENTEE);
            if (appointment.getStatus().equals(Status.RESCHEDULE_REQUESTED)) {
                  notification.setMessage("Session reschedule request on date " + appointment.getDate() + " time "
                              + appointment.getStartTime() + " to " + appointment.getEndTime()
                              + " is rejected by the mentor " + appointment.getMentee().getName() + ", because of "
                              + reason + ". The original date and time remain same.");
            } else {
                  notification.setMessage(
                              "Session request on date " + appointment.getDate() + " time " + appointment.getStartTime()
                                          + " to " + appointment.getEndTime() + " is rejected by the mentor "
                                          + appointment.getMentee().getName() + ", because of " + reason);
            }
            notification.setIsRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationsRepository.save(notification);

            appointment.setStatus(Status.REJECTED);
            appointment.setReasonByMentorIfRejecting(reason);
      }

      @Override
      public void cancleAppointment(Integer appointmentId, String reasong) throws SkillBuilderException {
            Appointments appointment = appointmentRepository.findById(appointmentId)
                        .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
            if (LocalDateTime.now().until(LocalDateTime.of(appointment.getDate(), appointment.getStartTime()),
                        ChronoUnit.HOURS) > 24) {
                  Mentor mentor = mentorRepository.findById(appointment.getMentor().getMentorId())
                              .orElseThrow(() -> new SkillBuilderException(userNotFound));
                  Mentee mentee = menteeRepository.findById(appointment.getMentee().getMenteeId())
                              .orElseThrow(() -> new SkillBuilderException(userNotFound));
                  Notifications notification = new Notifications();
                  notification.setSenderId(mentee.getMenteeId());
                  notification.setSenderType(UserType.MENTEE);
                  notification.setReceiverId(mentor.getMentorId());
                  notification.setReceiverType(UserType.MENTOR);
                  notification.setMessage("The appointment on date " + appointment.getDate() + " and time "
                              + appointment.getStartTime() + " is CANCLED by the mentee " + mentee.getName()
                              + ", beacuse of the reason- " + reasong);
                  notification.setIsRead(false);
                  notification.setCreatedAt(LocalDateTime.now());
                  notificationsRepository.save(notification);

                  for (int i = 0; i < mentor.getMentorAvailability().size(); i++) {
                        if (mentor.getMentorAvailability().get(i).getDate().equals(appointment.getDate()) &&
                                    mentor.getMentorAvailability().get(i).getStartTime()
                                                .equals(appointment.getStartTime())
                                    &&
                                    mentor.getMentorAvailability().get(i).getEndTime()
                                                .equals(appointment.getEndTime())) {
                              mentor.getMentorAvailability().get(i).setStatus(BookingStatus.AVAILABLE);
                              appointment.setStatus(Status.CANCELLED);
                              break;
                        }
                  }
            } else {
                  throw new SkillBuilderException("Service.CANCELLATION_NOT_AVAILABLE");
            }
      }

      @Override
      public Integer rescheduleAppointment(Integer appointmentId, AppointmentsDTO appointmentsDTO)
                  throws SkillBuilderException {
            Appointments prevAppointment = appointmentRepository.findById(appointmentId)
                        .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
            if (LocalDateTime.now().until(LocalDateTime.of(prevAppointment.getDate(), prevAppointment.getStartTime()),
                        ChronoUnit.HOURS) > 24) {
                  if (prevAppointment.getDate().equals(appointmentsDTO.getDate())
                              && prevAppointment.getStartTime().equals(appointmentsDTO.getStartTime())) {
                        throw new SkillBuilderException("Service.RESCHEDULE_REQUESTED_ON_SAME_SLOT");
                  }
                  Appointments appointment = mapper.map(appointmentsDTO, Appointments.class);
                  Mentor mentor = mentorRepository.findById(appointmentsDTO.getMentor().getMentorId())
                              .orElseThrow(() -> new SkillBuilderException(userNotFound));
                  Mentee mentee = menteeRepository.findById(appointmentsDTO.getMentee().getMenteeId())
                              .orElseThrow(() -> new SkillBuilderException(userNotFound));

                  appointment.setMentor(mentor);
                  appointment.setMentee(mentee);
                  appointment.setStatus(Status.RESCHEDULE_REQUESTED);
                  appointment.setCreatedAt(LocalDateTime.now());
                  appointment.setRescheduledId(appointmentId);

                  for (int i = 0; i < mentor.getMentorAvailability().size(); i++) {
                        if (mentor.getMentorAvailability().get(i).getDate().equals(appointment.getDate()) &&
                                    mentor.getMentorAvailability().get(i).getStartTime()
                                                .equals(appointment.getStartTime())
                                    &&
                                    mentor.getMentorAvailability().get(i).getEndTime().equals(appointment.getEndTime())
                                    &&
                                    mentor.getMentorAvailability().get(i).getStatus().equals(BookingStatus.AVAILABLE)) {
                              Integer id = appointmentRepository.save(appointment).getAppointmentId();
                              if (id != null) {
                                    Notifications notification = new Notifications();
                                    notification.setSenderId(mentee.getMenteeId());
                                    notification.setSenderType(UserType.MENTEE);
                                    notification.setReceiverId(mentor.getMentorId());
                                    notification.setReceiverType(UserType.MENTOR);
                                    notification.setMessage("Session reschedule requested from " + mentee.getName()
                                                + " on date " + appointment.getDate() + " time "
                                                + appointment.getStartTime() + " to " + appointment.getEndTime()
                                                + ", instead of date " + prevAppointment.getDate() + " time "
                                                + prevAppointment.getStartTime() + " to "
                                                + prevAppointment.getEndTime());
                                    notification.setIsRead(false);
                                    notification.setCreatedAt(LocalDateTime.now());
                                    notificationsRepository.save(notification);
                              }
                              return id;
                        }
                  }
                  throw new SkillBuilderException("Service.MENTOR_UNAVAILABLE_FOR_BOOKING");
            }
            throw new SkillBuilderException("Service.RESCHEDULE_NOT_AVAILABLE");
      }

      @Override
      public Integer getUpcommingSessionsByMentor(Integer mentorId) throws SkillBuilderException {
            return appointmentRepository.getUpcommingSessionsByMentor(mentorId);
      }

      @Override
      public Integer getCompletedSessionsByMentor(Integer mentorId) throws SkillBuilderException {
            return appointmentRepository.getCompletedSessionsByMentor(mentorId);
      }

      @Override
      public Integer getConnectedMenteesByMentor(Integer mentorId) throws SkillBuilderException {
            return appointmentRepository.getConnectedMenteesByMentor(mentorId);
      }

      @Override
      public Double getSessionsComplitionRateByMentor(Integer mentorId) throws SkillBuilderException {
            Integer completed = appointmentRepository.getCompletedSessionsByMentor(mentorId);
            Integer total = appointmentRepository.getTotalAppointmentsByMentor(mentorId);
            return (completed * 1.0 / total) * 100;
      }

      @Override
      public Double getRebookingRateByMentor(Integer mentorId) throws SkillBuilderException {
            Mentor mentor = mentorRepository.findById(mentorId)
                        .orElseThrow(() -> new SkillBuilderException(userNotFound));
            List<Appointments> appointments = appointmentRepository.findByMentor(mentor);

            Map<Mentee, Long> menteeAppointmentCount = appointments.stream()
                        .collect(Collectors.groupingBy(
                                    Appointments::getMentee,
                                    Collectors.counting()));
            Long rebookedMentees = menteeAppointmentCount.values().stream()
                        .filter(count -> count > 1)
                        .count();
            Integer totalMentee = menteeAppointmentCount.size();

            if (totalMentee == 0) {
                  return 0.0;
            }
            return (rebookedMentees * 1.0 / totalMentee) * 100;
      }

      @Override
      public List<String> getLatestAgendasByMentorId(Integer mentorId) throws SkillBuilderException {
            List<String> agendas = appointmentRepository.getLatestAgendasByMentorId(mentorId);
            if (agendas.size() > 6) {
                  return agendas.subList(0, 6);
            }
            return agendas;
      }

      @Override
      public List<DailyBookingCounts> findDailyBookingCountsForLast7Days() throws SkillBuilderException {
            List<Object[]> results = appointmentRepository.findDailyBookingCountsForLast7Days();
            return results.stream().map(data -> new DailyBookingCounts(
                        LocalDate.parse(data[0].toString()),
                        ((Number) data[1]).longValue())).toList();
      }

      @Override
      public List<WeeklyBookingCounts> findWeeklyBookingCountsForLast7Weeks() throws SkillBuilderException {
            List<Object[]> results = appointmentRepository.findWeeklyBookingCountsForLast7Weeks();
            return results.stream().map(data -> new WeeklyBookingCounts(
                        ((Number) data[0]).longValue(),
                        ((Number) data[1]).longValue(),
                        ((Number) data[2]).longValue())).toList();
      }

      @Override
      public List<MonthlyBookingCounts> findMonthlyBookingCountsForLast7Months() throws SkillBuilderException {
            List<Object[]> results = appointmentRepository.findMonthlyBookingCountsForLast7Months();
            return results.stream().map(data -> new MonthlyBookingCounts(
                        ((Number) data[0]).longValue(),
                        ((Number) data[1]).longValue(),
                        ((Number) data[2]).longValue())).toList();
      }

      @Override
      public List<UserRetentionDTO> getUserRetentionStats() throws SkillBuilderException {
            List<Object[]> results = appointmentRepository.getUserRetentionData();
            return results.stream().map(row -> new UserRetentionDTO(
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).doubleValue())).toList();
      }

      @Override
      public double getSessionCompletionRate() throws SkillBuilderException {
            List<Object[]> statsList = appointmentRepository.getSessionCompletionStats();
            if (statsList.isEmpty()) {
                  return 0.0;
            }
            Object[] stats = statsList.get(0);
            long totalSessions = ((Number) stats[0]).longValue();
            long completedSessions = ((Number) stats[1]).longValue();
            if (totalSessions == 0)
                  return 0.0;
            return (completedSessions * 100.0) / totalSessions;
      }

      @Override
      public double getAverageSessionDuration() throws SkillBuilderException {
            Double avgDuration = appointmentRepository.getAverageSessionDuration();
            return avgDuration != null ? avgDuration : 0.0;
      }

      @Override
      public Integer getUpcommingSessionsByMentee(Integer menteeId) throws SkillBuilderException {
            return appointmentRepository.getUpcommingSessionsByMentee(menteeId);
      }

      @Override
      public Integer getCompletedSessionsByMentee(Integer menteeId) throws SkillBuilderException {
            return appointmentRepository.getCompletedSessionsByMentee(menteeId);
      }

      @Override
      public Integer getTotalSessionDurationByMentee(Integer menteeId) throws SkillBuilderException {
            return appointmentRepository.getTotalSessionDurationByMentee(menteeId);
      }

      @Override
      public AppointmentsDTO getNextSessionByMentee(Integer menteeID) throws SkillBuilderException {
            Appointments a = appointmentRepository.getNextSessionByMentee(menteeID);
            if (a == null) {
                  return null;
            }
            AppointmentsDTO app = new AppointmentsDTO();
            app.setAppointmentId(a.getAppointmentId());
            app.setAgenda(a.getAgenda());
            app.setDuration(a.getDuration());
            app.setStartTime(a.getStartTime());
            app.setEndTime(a.getEndTime());
            app.setStatus(a.getStatus());
            app.setDate(a.getDate());
            app.setCreatedAt(a.getCreatedAt());
            app.setNoteByMentee(a.getNoteByMentee());
            app.setReasonByMentorIfRejecting(a.getReasonByMentorIfRejecting());
            app.setMentee(new MenteeDTO());
            app.getMentee().setMenteeId(a.getMentee().getMenteeId());
            app.setMentor(new MentorDTO());
            app.getMentor().setMentorId(a.getMentor().getMentorId());
            if (a.getReview() != null) {
                  app.setReview(mapper.map(a.getReview(), ReviewsDTO.class));
            }
            return app;
      }

      @Override
      public void completeSession(Integer appointmentId) throws SkillBuilderException {
            Appointments appointment = appointmentRepository.findById(appointmentId)
                        .orElseThrow(() -> new SkillBuilderException(appointmentNotFound));
            Notifications notification = new Notifications();
            notification.setSenderId(appointment.getMentor().getMentorId());
            notification.setSenderType(UserType.MENTOR);
            notification.setReceiverId(appointment.getMentee().getMenteeId());
            notification.setReceiverType(UserType.MENTEE);
            notification.setMessage("The session for " + appointment.getAgenda()
                        + " is now completed. Give your valuable feedback in the appointment's completed tab.");
            notification.setIsRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationsRepository.save(notification);

            appointment.setStatus(Status.COMPLETED);
      }
}