CREATE SCHEMA IF NOT EXISTS leetcode;
CREATE TABLE cron_jobs
(
	id          SERIAL PRIMARY KEY,
	name        VARCHAR(255),
	description TEXT,
	schedule    VARCHAR(255),
	enabled     BOOLEAN DEFAULT TRUE,
	last_run_at TIMESTAMP,
	next_run_at TIMESTAMP
);
