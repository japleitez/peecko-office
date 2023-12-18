import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVideo, NewVideo } from '../video.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVideo for edit and NewVideoFormGroupInput for create.
 */
type VideoFormGroupInput = IVideo | PartialWithRequiredKeyOf<NewVideo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVideo | NewVideo> = Omit<T, 'created' | 'released' | 'archived'> & {
  created?: string | null;
  released?: string | null;
  archived?: string | null;
};

type VideoFormRawValue = FormValueOf<IVideo>;

type NewVideoFormRawValue = FormValueOf<NewVideo>;

type VideoFormDefaults = Pick<NewVideo, 'id' | 'created' | 'released' | 'archived'>;

type VideoFormGroupContent = {
  id: FormControl<VideoFormRawValue['id'] | NewVideo['id']>;
  code: FormControl<VideoFormRawValue['code']>;
  title: FormControl<VideoFormRawValue['title']>;
  duration: FormControl<VideoFormRawValue['duration']>;
  language: FormControl<VideoFormRawValue['language']>;
  player: FormControl<VideoFormRawValue['player']>;
  thumbnail: FormControl<VideoFormRawValue['thumbnail']>;
  url: FormControl<VideoFormRawValue['url']>;
  audience: FormControl<VideoFormRawValue['audience']>;
  intensity: FormControl<VideoFormRawValue['intensity']>;
  tags: FormControl<VideoFormRawValue['tags']>;
  filename: FormControl<VideoFormRawValue['filename']>;
  description: FormControl<VideoFormRawValue['description']>;
  created: FormControl<VideoFormRawValue['created']>;
  released: FormControl<VideoFormRawValue['released']>;
  archived: FormControl<VideoFormRawValue['archived']>;
  videoCategory: FormControl<VideoFormRawValue['videoCategory']>;
  coach: FormControl<VideoFormRawValue['coach']>;
};

export type VideoFormGroup = FormGroup<VideoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VideoFormService {
  createVideoFormGroup(video: VideoFormGroupInput = { id: null }): VideoFormGroup {
    const videoRawValue = this.convertVideoToVideoRawValue({
      ...this.getFormDefaults(),
      ...video,
    });
    return new FormGroup<VideoFormGroupContent>({
      id: new FormControl(
        { value: videoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(videoRawValue.code, {
        validators: [Validators.required],
      }),
      title: new FormControl(videoRawValue.title, {
        validators: [Validators.required],
      }),
      duration: new FormControl(videoRawValue.duration),
      language: new FormControl(videoRawValue.language, {
        validators: [Validators.required],
      }),
      player: new FormControl(videoRawValue.player, {
        validators: [Validators.required],
      }),
      thumbnail: new FormControl(videoRawValue.thumbnail),
      url: new FormControl(videoRawValue.url),
      audience: new FormControl(videoRawValue.audience),
      intensity: new FormControl(videoRawValue.intensity),
      tags: new FormControl(videoRawValue.tags),
      filename: new FormControl(videoRawValue.filename),
      description: new FormControl(videoRawValue.description),
      created: new FormControl(videoRawValue.created),
      released: new FormControl(videoRawValue.released),
      archived: new FormControl(videoRawValue.archived),
      videoCategory: new FormControl(videoRawValue.videoCategory),
      coach: new FormControl(videoRawValue.coach),
    });
  }

  getVideo(form: VideoFormGroup): IVideo | NewVideo {
    return this.convertVideoRawValueToVideo(form.getRawValue() as VideoFormRawValue | NewVideoFormRawValue);
  }

  resetForm(form: VideoFormGroup, video: VideoFormGroupInput): void {
    const videoRawValue = this.convertVideoToVideoRawValue({ ...this.getFormDefaults(), ...video });
    form.reset(
      {
        ...videoRawValue,
        id: { value: videoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VideoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      released: currentTime,
      archived: currentTime,
    };
  }

  private convertVideoRawValueToVideo(rawVideo: VideoFormRawValue | NewVideoFormRawValue): IVideo | NewVideo {
    return {
      ...rawVideo,
      created: dayjs(rawVideo.created, DATE_TIME_FORMAT),
      released: dayjs(rawVideo.released, DATE_TIME_FORMAT),
      archived: dayjs(rawVideo.archived, DATE_TIME_FORMAT),
    };
  }

  private convertVideoToVideoRawValue(
    video: IVideo | (Partial<NewVideo> & VideoFormDefaults),
  ): VideoFormRawValue | PartialWithRequiredKeyOf<NewVideoFormRawValue> {
    return {
      ...video,
      created: video.created ? video.created.format(DATE_TIME_FORMAT) : undefined,
      released: video.released ? video.released.format(DATE_TIME_FORMAT) : undefined,
      archived: video.archived ? video.archived.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
