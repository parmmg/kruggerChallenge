package com.krugger.challenge.service.impl;

import com.krugger.challenge.exception.ValidationException;
import com.krugger.challenge.repository.VaccineRepository;
import com.krugger.challenge.entity.Vaccine;
import com.krugger.challenge.presentation.presenter.VaccinePresenter;
import com.krugger.challenge.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class VaccineServiceImpl implements VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Override
    public List<VaccinePresenter> getVaccines() {
        Iterable<Vaccine> vaccines = vaccineRepository.findAll();
        List<VaccinePresenter> vaccinePresenters = new ArrayList<>();
        vaccines.forEach(vaccine -> {
            vaccinePresenters.add(vaccineToVaccinePresenter(vaccine));
        });
        return vaccinePresenters;
    }

    @Override
    public Vaccine findById(UUID id) {
        Optional<Vaccine> vaccine = vaccineRepository.findById(id);
        if(vaccine.isPresent()){
            return vaccine.get();
        }
        throw new ValidationException("Vaccine not found");
    }

    @Override
    public VaccinePresenter vaccineToVaccinePresenter(Vaccine vaccine) {
        if (vaccine==null)
            return null;
        return VaccinePresenter.builder()
                .id(vaccine.getId())
                .name(vaccine.getName())
                .description(vaccine.getDescription())
                .build();
    }

    @Override
    public Vaccine vaccinePresenterToVaccine(VaccinePresenter vaccinePresenter) {
        if (vaccinePresenter==null)
            return null;
        return Vaccine.builder()
                .id(vaccinePresenter.getId())
                .name(vaccinePresenter.getName())
                .description(vaccinePresenter.getDescription())
                .build();
    }
}
