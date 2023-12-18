import dayjs from 'dayjs/esm';

import { IVideo, NewVideo } from './video.model';

export const sampleWithRequiredData: IVideo = {
  id: 3524,
  code: 'mmm teethe dude',
  title: 'quarrelsomely',
  language: 'ES',
  player: 'PEECKO',
};

export const sampleWithPartialData: IVideo = {
  id: 765,
  code: 'rigidly',
  title: 'meanwhile unique warmly',
  language: 'FR',
  player: 'PEECKO',
  url: 'https://tender-bench.biz/',
  audience: 'incidentally',
  intensity: 'ADVANCED',
  description: 'eminent',
  created: dayjs('2023-12-18T09:17'),
  archived: dayjs('2023-12-17T19:09'),
};

export const sampleWithFullData: IVideo = {
  id: 14089,
  code: 'whereas lest',
  title: 'for tweet',
  duration: 32620,
  language: 'EN',
  player: 'YOUTUBE',
  thumbnail: 'paragraph diverge',
  url: 'https://united-lipoprotein.com',
  audience: 'evenly alpaca',
  intensity: 'INTERMEDIATE',
  tags: 'aha ick',
  filename: 'within',
  description: 'drat',
  created: dayjs('2023-12-17T12:10'),
  released: dayjs('2023-12-18T10:00'),
  archived: dayjs('2023-12-17T14:47'),
};

export const sampleWithNewData: NewVideo = {
  code: 'bah supposing',
  title: 'cobbler or',
  language: 'FR',
  player: 'PEECKO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
