INSERT INTO public.orders (id_order, id_product, id_user, id_status, reservation_date, delivery_date, delivery_status)
VALUES
    (1, 1, 1, 1, '2024-10-01', '2024-10-02', true)
    ON CONFLICT (id_order) DO NOTHING;

INSERT INTO public.orders (id_order, id_product, id_user, id_status, reservation_date, delivery_date, delivery_status)
VALUES
    (2, 2, 2, 1, '2024-10-01', '2024-10-03', false)
    ON CONFLICT (id_order) DO NOTHING;

INSERT INTO public.orders (id_order, id_product, id_user, id_status, reservation_date, delivery_date, delivery_status)
VALUES
    (3, 3, 1, 2, '2024-10-02', '2024-10-04', true)
    ON CONFLICT (id_order) DO NOTHING;
