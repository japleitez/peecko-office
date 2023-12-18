import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IArticleItem } from '../article-item.model';
import { ArticleItemService } from '../service/article-item.service';

@Component({
  standalone: true,
  templateUrl: './article-item-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ArticleItemDeleteDialogComponent {
  articleItem?: IArticleItem;

  constructor(
    protected articleItemService: ArticleItemService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.articleItemService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
