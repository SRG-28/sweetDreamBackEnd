INSERT INTO public.category (id_category, name, description) VALUES
    (1, 'Chocolate Cake', 'Rich and moist chocolate cake')
    ON CONFLICT (id_category) DO NOTHING;

INSERT INTO public.category (id_category, name, description) VALUES
    (2, 'Vanilla Cake', 'Classic vanilla sponge cake')
    ON CONFLICT (id_category) DO NOTHING;

INSERT INTO public.category (id_category, name, description) VALUES
    (3, 'Red Velvet Cake', 'Delicious red velvet cake with cream cheese frosting')
    ON CONFLICT (id_category) DO NOTHING;

