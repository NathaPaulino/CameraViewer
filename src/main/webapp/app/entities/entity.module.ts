import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CameraViewerUserPermissionModule } from './user-permission/user-permission.module';
import { CameraViewerCameraModule } from './camera/camera.module';
import { CameraViewerScreenModule } from './screen/screen.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CameraViewerUserPermissionModule,
        CameraViewerCameraModule,
        CameraViewerScreenModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CameraViewerEntityModule {}
