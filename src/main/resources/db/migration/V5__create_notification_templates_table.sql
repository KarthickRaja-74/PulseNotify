CREATE TABLE notification_templates (
    id UUID PRIMARY KEY,

    template_key VARCHAR(100) NOT NULL,
    channel VARCHAR(20) NOT NULL,
    subject VARCHAR(255),
    body TEXT NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_template_key_channel
        UNIQUE (template_key, channel),

    CONSTRAINT chk_template_channel
        CHECK (channel IN ('EMAIL', 'SMS')),

    CONSTRAINT chk_template_subject
        CHECK (
            (
                channel = 'EMAIL'
                AND subject IS NOT NULL
                AND length(trim(subject)) > 0
            )
            OR
            (
                channel = 'SMS'
                AND subject IS NULL
            )
        )
);