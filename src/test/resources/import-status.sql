INSERT INTO public.status (id_status, name) VALUES
    (1, 'Not delivered')
    ON CONFLICT (id_status) DO NOTHING;

INSERT INTO public.status (id_status, name) VALUES
    (2, 'Pending')
    ON CONFLICT (id_status) DO NOTHING;

INSERT INTO public.status (id_status, name) VALUES
    (3, 'Delivered')
    ON CONFLICT (id_status) DO NOTHING;
