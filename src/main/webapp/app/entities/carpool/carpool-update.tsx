import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPassenger } from 'app/shared/model/passenger.model';
import { getEntities as getPassengers } from 'app/entities/passenger/passenger.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { ICarpool } from 'app/shared/model/carpool.model';
import { getEntity, updateEntity, createEntity, reset } from './carpool.reducer';

export const CarpoolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const passengers = useAppSelector(state => state.passenger.entities);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const carpoolEntity = useAppSelector(state => state.carpool.entity);
  const loading = useAppSelector(state => state.carpool.loading);
  const updating = useAppSelector(state => state.carpool.updating);
  const updateSuccess = useAppSelector(state => state.carpool.updateSuccess);

  const handleClose = () => {
    navigate('/carpool' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPassengers({}));
    dispatch(getUtilisateurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.departureTime = convertDateTimeToServer(values.departureTime);
    if (values.seatsAvailable !== undefined && typeof values.seatsAvailable !== 'number') {
      values.seatsAvailable = Number(values.seatsAvailable);
    }
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }

    const entity = {
      ...carpoolEntity,
      ...values,
      passengers: mapIdList(values.passengers),
      driver: utilisateurs.find(it => it.id.toString() === values.driver?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          departureTime: displayDefaultDateTime(),
        }
      : {
          ...carpoolEntity,
          departureTime: convertDateTimeFromServer(carpoolEntity.departureTime),
          passengers: carpoolEntity?.passengers?.map(e => e.id.toString()),
          driver: carpoolEntity?.driver?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.carpool.home.createOrEditLabel" data-cy="CarpoolCreateUpdateHeading">
            <Translate contentKey="gofindApp.carpool.home.createOrEditLabel">Create or edit a Carpool</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="carpool-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gofindApp.carpool.origin')}
                id="carpool-origin"
                name="origin"
                data-cy="origin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gofindApp.carpool.destination')}
                id="carpool-destination"
                name="destination"
                data-cy="destination"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gofindApp.carpool.departureTime')}
                id="carpool-departureTime"
                name="departureTime"
                data-cy="departureTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gofindApp.carpool.seatsAvailable')}
                id="carpool-seatsAvailable"
                name="seatsAvailable"
                data-cy="seatsAvailable"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('gofindApp.carpool.description')}
                id="carpool-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('gofindApp.carpool.price')} id="carpool-price" name="price" data-cy="price" type="text" />
              <ValidatedField
                label={translate('gofindApp.carpool.passengers')}
                id="carpool-passengers"
                data-cy="passengers"
                type="select"
                multiple
                name="passengers"
              >
                <option value="" key="0" />
                {passengers
                  ? passengers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="carpool-driver"
                name="driver"
                data-cy="driver"
                label={translate('gofindApp.carpool.driver')}
                type="select"
              >
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carpool" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CarpoolUpdate;
