import dayjs from 'dayjs/esm';
import { IApsDevice } from 'app/entities/aps-device/aps-device.model';
import { IPlayList } from 'app/entities/play-list/play-list.model';
import { IArticleList } from 'app/entities/article-list/article-list.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IApsUser {
  id: number;
  name?: string | null;
  username?: string | null;
  usernameVerified?: keyof typeof YesNo | null;
  privateEmail?: string | null;
  privateVerified?: keyof typeof YesNo | null;
  language?: keyof typeof Language | null;
  license?: string | null;
  active?: keyof typeof YesNo | null;
  password?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  apsDevices?: IApsDevice[] | null;
  playLists?: IPlayList[] | null;
  articleLists?: IArticleList[] | null;
}

export type NewApsUser = Omit<IApsUser, 'id'> & { id: null };
