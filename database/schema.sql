-- MedLink PostgreSQL Schema
-- Database: medlink

-- Create UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- USERS TABLE
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'PATIENT',
    active BOOLEAN DEFAULT true,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_phone ON users(phone);
CREATE INDEX idx_users_role ON users(role);

-- HOSPITALS TABLE
CREATE TABLE hospitals (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    hospital_name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(255),
    registration_number VARCHAR(100) UNIQUE,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_hospitals_name ON hospitals(hospital_name);
CREATE INDEX idx_hospitals_city ON hospitals(city);

-- PATIENTS TABLE
CREATE TABLE patients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_unique_id VARCHAR(100) UNIQUE NOT NULL,
    user_id UUID NOT NULL UNIQUE,
    aadhaar_number VARCHAR(12),
    blood_group VARCHAR(5),
    gender VARCHAR(20),
    date_of_birth DATE,
    emergency_contact VARCHAR(20),
    emergency_contact_name VARCHAR(255),
    address VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_patients_user_id ON patients(user_id);
CREATE INDEX idx_patients_phone ON patients USING btree ((CAST(patient_unique_id AS text))));
CREATE INDEX idx_patients_aadhaar ON patients(aadhaar_number);

-- DOCTORS TABLE
CREATE TABLE doctors (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL UNIQUE,
    hospital_id UUID NOT NULL,
    specialization VARCHAR(255),
    license_number VARCHAR(100) UNIQUE,
    experience_years INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE INDEX idx_doctors_hospital_id ON doctors(hospital_id);
CREATE INDEX idx_doctors_user_id ON doctors(user_id);
CREATE INDEX idx_doctors_specialization ON doctors(specialization);

-- PATIENT_ALLERGIES TABLE
CREATE TABLE patient_allergies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    allergy_name VARCHAR(255) NOT NULL,
    severity VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

CREATE INDEX idx_allergies_patient_id ON patient_allergies(patient_id);

-- MEDICAL_HISTORY TABLE
CREATE TABLE medical_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    disease_name VARCHAR(255) NOT NULL,
    diagnosis_date DATE,
    status VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

CREATE INDEX idx_medical_history_patient_id ON medical_history(patient_id);

-- HOSPITAL_VISITS TABLE
CREATE TABLE hospital_visits (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    hospital_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    visit_date DATE NOT NULL,
    diagnosis TEXT,
    treatment_summary TEXT,
    follow_up_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE INDEX idx_visits_patient_id ON hospital_visits(patient_id);
CREATE INDEX idx_visits_hospital_id ON hospital_visits(hospital_id);
CREATE INDEX idx_visits_doctor_id ON hospital_visits(doctor_id);
CREATE INDEX idx_visits_date ON hospital_visits(visit_date);

-- PRESCRIPTIONS TABLE
CREATE TABLE prescriptions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    visit_id UUID NOT NULL,
    medicine_name VARCHAR(255) NOT NULL,
    dosage VARCHAR(100),
    frequency VARCHAR(100),
    duration_days INTEGER,
    instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (visit_id) REFERENCES hospital_visits(id) ON DELETE CASCADE
);

CREATE INDEX idx_prescriptions_visit_id ON prescriptions(visit_id);

-- LAB_REPORTS TABLE
CREATE TABLE lab_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    hospital_id UUID NOT NULL,
    report_name VARCHAR(255) NOT NULL,
    report_type VARCHAR(100),
    report_file_url VARCHAR(500),
    report_date DATE,
    test_name VARCHAR(255),
    findings TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE INDEX idx_lab_reports_patient_id ON lab_reports(patient_id);
CREATE INDEX idx_lab_reports_hospital_id ON lab_reports(hospital_id);
CREATE INDEX idx_lab_reports_date ON lab_reports(report_date);

-- SCAN_REPORTS TABLE
CREATE TABLE scan_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    hospital_id UUID NOT NULL,
    scan_name VARCHAR(255) NOT NULL,
    scan_type VARCHAR(100),
    file_url VARCHAR(500),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    findings TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE INDEX idx_scan_reports_patient_id ON scan_reports(patient_id);
CREATE INDEX idx_scan_reports_hospital_id ON scan_reports(hospital_id);
CREATE INDEX idx_scan_reports_type ON scan_reports(scan_type);

-- VACCINATIONS TABLE
CREATE TABLE vaccinations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    vaccine_name VARCHAR(255) NOT NULL,
    dose_number INTEGER,
    vaccination_date DATE NOT NULL,
    administered_by UUID,
    hospital_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (administered_by) REFERENCES doctors(id),
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE INDEX idx_vaccinations_patient_id ON vaccinations(patient_id);
CREATE INDEX idx_vaccinations_date ON vaccinations(vaccination_date);

-- OTP_REQUESTS TABLE
CREATE TABLE otp_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    otp_code VARCHAR(6) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    verified BOOLEAN DEFAULT false,
    verified_at TIMESTAMP,
    attempt_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

CREATE INDEX idx_otp_patient_id ON otp_requests(patient_id);
CREATE INDEX idx_otp_expires_at ON otp_requests(expires_at);

-- ACCESS_REQUESTS TABLE
CREATE TABLE access_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    requesting_hospital_id UUID NOT NULL,
    requesting_doctor_id UUID NOT NULL,
    request_status VARCHAR(50) DEFAULT 'PENDING',
    otp_verified BOOLEAN DEFAULT false,
    otp_id UUID,
    approved_at TIMESTAMP,
    expires_at TIMESTAMP,
    rejection_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (requesting_hospital_id) REFERENCES hospitals(id),
    FOREIGN KEY (requesting_doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (otp_id) REFERENCES otp_requests(id)
);

CREATE INDEX idx_access_requests_patient_id ON access_requests(patient_id);
CREATE INDEX idx_access_requests_status ON access_requests(request_status);
CREATE INDEX idx_access_requests_doctor_id ON access_requests(requesting_doctor_id);
CREATE INDEX idx_access_requests_expires ON access_requests(expires_at);

-- AUDIT_LOGS TABLE
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID,
    action_type VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100),
    entity_id UUID,
    old_value TEXT,
    new_value TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_audit_user_id ON audit_logs(user_id);
CREATE INDEX idx_audit_action ON audit_logs(action_type);
CREATE INDEX idx_audit_timestamp ON audit_logs(timestamp);
CREATE INDEX idx_audit_entity ON audit_logs(entity_name, entity_id);

-- FILES TABLE
CREATE TABLE files (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    uploaded_by UUID NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(100),
    file_url VARCHAR(500) NOT NULL,
    file_size BIGINT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_files_patient_id ON files(patient_id);
CREATE INDEX idx_files_uploaded_by ON files(uploaded_by);

-- REFRESH_TOKENS TABLE
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    token_value VARCHAR(500) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_expires ON refresh_tokens(expires_at);

-- HOSPITAL_ADMINS TABLE
CREATE TABLE hospital_admins (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL UNIQUE,
    hospital_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE INDEX idx_hospital_admins_hospital_id ON hospital_admins(hospital_id);

-- SEED DATA (Optional - for testing)
INSERT INTO users (first_name, last_name, email, phone, password_hash, role, active)
VALUES ('Super', 'Admin', 'superadmin@medlink.com', '+919999999999', '$2a$10$slYQmyNdGzin7olVN3p5Be7DQwvL5QpIzr0bHDL3a6F0p9u8SRrEa', 'SUPER_ADMIN', true)
ON CONFLICT DO NOTHING;