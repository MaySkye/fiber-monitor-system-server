
-- drop table  telemetry
CREATE TABLE telemetry  (
  id INTEGER  NOT NULL UNIQUE ,
  site_name varchar(25)   DEFAULT NULL ,
  device_name varchar(255)   DEFAULT NULL ,
  data_name varchar(255)   DEFAULT NULL,
  timestamp varchar(50)   DEFAULT NULL,
  detected_value varchar(100)   DEFAULT NULL ,
  base varchar(255)   DEFAULT NULL,
  offset INTEGER   DEFAULT NULL ,
  ratio INTEGER   DEFAULT NULL ,
  upper_limit varchar(50)   DEFAULT NULL,
  lower_limit varchar(50)   DEFAULT NULL,
  alarm_upper_limit varchar(50)   DEFAULT NULL,
  alarm_lower_limit varchar(50)   DEFAULT NULL,
  state INTEGER   DEFAULT NULL,
  blocked INTEGER  DEFAULT NULL,
  alarm_state varchar(255)   DEFAULT NULL,
  PRIMARY KEY (id)
)


INSERT INTO telemetry VALUES (1, 'xian', 'optical_frequency_send_device1', 'input_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (2, 'xian', 'optical_frequency_send_device2', 'input_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (3, 'xian', 'optical_frequency_send_device3', 'input_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (4, 'xian', 'optical_frequency_send_device4', 'input_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (5, 'xian', 'optical_frequency_send_device1', 'output_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (6, 'xian', 'optical_frequency_send_device2', 'output_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (7, 'xian', 'optical_frequency_send_device3', 'output_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (8, 'xian', 'optical_frequency_send_device4', 'output_power', NULL, '7.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (9, 'xian', 'optical_frequency_send_device1', 'AOM_drive_power', NULL, '2.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (10, 'xian', 'optical_frequency_send_device2', 'AOM_drive_power', NULL, '2.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (11, 'xian', 'optical_frequency_send_device3', 'AOM_drive_power', NULL, '2.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (12, 'xian', 'optical_frequency_send_device4', 'AOM_drive_power', NULL, '2.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (13, 'xian', 'optical_frequency_send_device1', 'beat_signal', NULL, '3.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (14, 'xian', 'optical_frequency_send_device2', 'beat_signal', NULL, '3.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (15, 'xian', 'optical_frequency_send_device3', 'beat_signal', NULL, '3.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (16, 'xian', 'optical_frequency_send_device4', 'beat_signal', NULL, '3.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (17, 'xian', 'optical_frequency_recv_device1', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (18, 'xian', 'optical_frequency_recv_device2', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (19, 'xian', 'optical_frequency_recv_device3', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (20, 'xian', 'optical_frequency_recv_device4', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (21, 'xian', 'optical_frequency_recv_device1', 'AOM_drive_power', NULL, '4.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (22, 'xian', 'optical_frequency_recv_device2', 'AOM_drive_power', NULL, '4.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (23, 'xian', 'optical_frequency_recv_device3', 'AOM_drive_power', NULL, '4.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (24, 'xian', 'optical_frequency_recv_device4', 'AOM_drive_power', NULL, '4.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (25, 'xian', 'multi_route_switch_device', 'current_route', NULL, '8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (26, 'xian', 'EDFA_relay_device1', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (27, 'xian', 'EDFA_relay_device2', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (28, 'xian', 'EDFA_relay_device1', 'output_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (29, 'xian', 'EDFA_relay_device2', 'output_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (30, 'xian', 'light_source_relay_device1', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (31, 'xian', 'light_source_relay_device2', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (32, 'xian', 'light_source_relay_device1', 'output_power', NULL, '20.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (33, 'xian', 'light_source_relay_device2', 'output_power', NULL, '20.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (34, 'xian', 'two_way_edfa_device1', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (35, 'xian', 'two_way_edfa_device2', 'input_power', NULL, '-13.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (36, 'xian', 'two_way_edfa_device1', 'output_power', NULL, '4.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (37, 'xian', 'two_way_edfa_device2', 'output_power', NULL, '4.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (38, 'xian', 'reference_cavity_laser_unit', 'input_power', NULL, '50.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (39, 'xian', 'reference_cavity_laser_unit', 'lon_pump_current', NULL, '30.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (40, 'xian', 'reference_cavity_laser_unit', 'transmission_peak_voltage', NULL, '15.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (41, 'xian', 'reference_cavity_laser_unit', 'AOM_output_power', NULL, '0.2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (42, 'xian', 'reference_cavity_laser_unit', 'AOM_drive_power', NULL, '1.2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (43, 'xian', 'reference_cavity_laser_unit', 'pre_cavity_output_voltage', NULL, '5.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (44, 'xian', 'reference_cavity_laser_unit', 'pre_cavity_output_RF', NULL, '20.5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (45, 'xian', 'reference_cavity_laser_unit', 'post_cavity_output_voltage', NULL, '5.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (46, 'xian', 'optical_fiber_laser_unit1', 'output_power', NULL, '6.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (47, 'xian', 'optical_fiber_laser_unit2', 'output_power', NULL, '6.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (48, 'xian', 'optical_fiber_laser_unit1', 'beat_signal_voltage', NULL, '3.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (49, 'xian', 'optical_fiber_laser_unit2', 'beat_signal_voltage', NULL, '3.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (50, 'xian', 'optical_fiber_laser_unit1', 'AOM_output_power', NULL, '0.2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (51, 'xian', 'optical_fiber_laser_unit2', 'AOM_output_power', NULL, '0.2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (52, 'xian', 'optical_fiber_laser_unit1', 'AOM_drive_power', NULL, '1.2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (53, 'xian', 'optical_fiber_laser_unit2', 'AOM_drive_power', NULL, '1.2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (54, 'xian', 'optical_fiber_laser_unit1', 'error_peak_value', NULL, '800.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (55, 'xian', 'optical_fiber_laser_unit2', 'error_peak_value', NULL, '800.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (56, 'xian', 'optical_fiber_laser_unit1', 'temperature', NULL, '42.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO telemetry VALUES (57, 'xian', 'optical_fiber_laser_unit2', 'temperature', NULL, '42.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
