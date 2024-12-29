SELECT lr.*
FROM lab_reservation lr
WHERE lr.week = 1 AND lr.day_of_week = 2
  AND NOT EXISTS (
    SELECT 1 FROM lab_reservation sub_lr
    WHERE sub_lr.lab_id = lr.lab_id
      AND sub_lr.week = lr.week
      AND sub_lr.day_of_week = lr.day_of_week
      AND sub_lr.section = lr.section
);
SELECT * FROM lab_reservation
WHERE week = 1 AND day_of_week = 2;


# 基于周/星期查询空闲节的可用教室
WITH AllSlots AS (
    SELECT
        l.id AS lab_id,
        l.name AS lab_name,
        w.week,
        d.day_of_week,
        s.section
    FROM
        lab l,
        (SELECT DISTINCT week FROM lab_reservation) w,
        (SELECT DISTINCT day_of_week FROM lab_reservation) d,
        (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
)
SELECT
    a.lab_id,
    a.lab_name,
    a.week,
    a.day_of_week,
    a.section
FROM
    AllSlots a
        LEFT JOIN lab_reservation lr
                  ON a.lab_id = lr.lab_id
                      AND a.week = lr.week
                      AND a.day_of_week = lr.day_of_week
                      AND a.section = lr.section
WHERE
    lr.id IS NULL
  AND a.week = 1
  AND a.day_of_week = 2
ORDER BY
    a.lab_id,
    a.section;

#基于周/星期/节，查询可用实验室
WITH AllSlots AS (
    SELECT
        l.id AS lab_id,
        l.name AS lab_name,
        w.week,
        d.day_of_week,
        s.section
    FROM
        lab l,
        (SELECT DISTINCT week FROM lab_reservation) w,
        (SELECT DISTINCT day_of_week FROM lab_reservation) d,
        (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
)
SELECT
    a.lab_id,
    a.lab_name,
    a.week,
    a.day_of_week,
    a.section
FROM
    AllSlots a
        LEFT JOIN lab_reservation lr
                  ON a.lab_id = lr.lab_id
                      AND a.week = lr.week
                      AND a.day_of_week = lr.day_of_week
                      AND a.section = lr.section
WHERE
    lr.id IS NULL
  AND a.week = :week
  AND a.day_of_week = :day_of_week
  AND a.section = :section
ORDER BY
    a.lab_id,
    a.section;

select * from lab_reservation
where week=1 and day_of_week = 2 and section = 1;


select * from lab_reservation
where week=1 and day_of_week = 4 and section = 4;