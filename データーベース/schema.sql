-- こちらを使用して、共有データベースにアクセスできない場合は、
-- ご自身のデータベースにテーブルを作成してください。
-- また、application.properties 内の情報も必ず更新してください。

-- Users
CREATE TABLE IF NOT EXISTS public.users (
    users_id serial PRIMARY KEY,
    name varchar(50) NOT NULL,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(250) NOT NULL,
    postcode varchar(10),
    address text,
    tel varchar(15),
    isactive boolean DEFAULT true,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);

-- Items
CREATE TABLE IF NOT EXISTS public.items (
    item_id serial PRIMARY KEY,
    users_id integer NOT NULL REFERENCES public.users(users_id),
    name varchar(50) NOT NULL,
    category integer NOT NULL,
    detail text NOT NULL,
    price integer NOT NULL,
    sale_status boolean NOT NULL DEFAULT true,
    buy_user integer REFERENCES public.users(users_id),
    images_path varchar(250) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    purchase_date timestamp
);

-- Payments
CREATE TABLE IF NOT EXISTS public.payments (
    card_id serial PRIMARY KEY,
    users_id integer NOT NULL REFERENCES public.users(users_id),
    card_number varchar NOT NULL,
    card_name varchar NOT NULL,
    security_code varchar NOT NULL,
    exp_date date NOT NULL
);

