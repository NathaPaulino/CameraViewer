import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Camera } from './camera.model';
import { CameraService } from './camera.service';
import { AuthServerProvider } from '../../shared/auth/auth-jwt.service';

@Component({
    selector: 'jhi-camera-detail',
    templateUrl: './camera-detail.component.html'
})
export class CameraDetailComponent implements OnInit, OnDestroy {

    camera: Camera;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cameraService: CameraService,
        private authServerProvider: AuthServerProvider,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCameras();
    }

    load(id) {
        this.cameraService.find(id).subscribe((camera) => {
            this.camera = camera;
            this.camera.accessURL = 'http://' + window.location.hostname + ':4200/?camera=' + this.camera.id + '&auth=' + this.authServerProvider.getToken();
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCameras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cameraListModification',
            (response) => this.load(this.camera.id)
        );
    }
}
