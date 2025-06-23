# Duels (Дуэли) — автономный PvP-режим для Mineland Network

## Описание
Duels — это модульный Spigot/Paper-плагин для организации дуэлей 1 на 1 с поддержкой очередей, режимов, World Instancing, PlaceholderAPI, экономики и гибкой настройки через config.yml.

## Возможности
- Очереди и арены для дуэлей 1 на 1
- GUI-выбор режима дуэли (classic, archer, random и др.)
- World Instancing (клонирование мира для каждой дуэли через Multiverse)
- Таймер боя, ничья по истечении времени
- Гибкая настройка предметов, арен, наград, блокируемых команд
- Экономика (Vault/PointsAPI), PlaceholderAPI (%duel_wins%, %duel_losses%, %duel_money%)
- Статистика, топ по победам, команды /duel leave, /duel stats, /duel top
- Визуальные эффекты, кастомные сообщения

## Установка и сборка
1. Склонируйте репозиторий:
   ```
   git clone ...
   ```
2. Откройте как Maven/Gradle проект или добавьте зависимости Spigot, Vault, Citizens, PlaceholderAPI, Multiverse-Core вручную.
3. Соберите jar-файл и поместите его в папку `plugins` вашего сервера.
4. Установите зависимости: Citizens, Vault, PlaceholderAPI, Multiverse-Core.
5. Перезапустите сервер.

## Настройка
- Все параметры (NPC, арены, режимы, награды, предметы, лобби, блокируемые команды) настраиваются в `config.yml`.
- Пример см. в файле config.yml.

## Лицензия
MIT License (см. LICENSE)

## Поддержка
- Discord: https://discord.com/invite/PhEvaF7
- Mineland: https://mineland.net 